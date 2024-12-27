package com.android.server.display.exynos;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Build;
import android.util.Log;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import java.io.File;
import java.util.TreeMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ExynosDisplayColor {
    public final String BYPASS_XML_FILE_PATH;
    public final String CGC17_CON_SYSFS_PATH;
    public final String CGC17_DEC_SYSFS_PATH;
    public final String CGC17_ENC_SYSFS_PATH;
    public final String CGC17_IDX_SYSFS_PATH;
    public final String CGC_DITHER_SYSFS_PATH;
    public final String CGC_SYSFS_PATH;
    public final String COLORMODE_XML_FILE_PATH;
    public final String COLORTEMP_XML_FILE_PATH;
    public final String DE_SYSFS_PATH;
    public final String EXTENSION_OFF;
    public final String EXTENSION_ON;
    public final String EYETEMP_XML_FILE_PATH;
    public final String GAMMA_EXT_SYSFS_PATH;
    public final String GAMMA_MATRIX_SYSFS_PATH;
    public final String GAMMA_SYSFS_PATH;
    public final String HSC_SYSFS_PATH;
    public final String HW_VER_8_0;
    public final String LINEAR_MATRIX_SYSFS_PATH;
    public final String RGBGAIN_XML_FILE_PATH;
    public final String SHARPNESS_XML_FILE_PATH;
    public final String SKINCOLOR_XML_FILE_PATH;
    public final String WHITEPOINT_XML_FILE_PATH;
    public boolean bIsColortempOn;
    public boolean bIsRgbWeightOn;
    public String[] bgain_array;
    public String[] cgc_dither_array;
    public final TreeMap colortemp_map;
    public String[] eyetemp_array;
    public String[] gamma_bypass_array;
    public String[] gamma_ext_bypass_array;
    public String[] gamma_matrix_bypass_array;
    public String[] ggain_array;
    public String[] hsc_bypass_array;
    public final String hw_ver;
    public String[] linear_matrix_bypass_array;
    public int mColortempConvMethod;
    public ExynosDisplayTune mExynosDisplayTune;
    public String[] rgain_array;
    public String[] sharpness_array;
    public String[] skincolor_array;
    public String[] whitepoint_array;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum eColortempMethod {
        /* JADX INFO: Fake field, exist only in values array */
        Bradford(new float[][]{new float[]{0.8951f, 0.2664f, -0.1614f}, new float[]{-0.750192f, 1.7135f, 0.036705f}, new float[]{0.0389f, -0.0685f, 1.0296f}}, new float[][]{new float[]{0.9869929f, -0.1470543f, 0.1599627f}, new float[]{0.4323053f, 0.5183603f, 0.0492912f}, new float[]{-0.0085287f, 0.0400428f, 0.9684867f}}),
        /* JADX INFO: Fake field, exist only in values array */
        VonKries(new float[][]{new float[]{0.40024f, 0.707608f, -0.080811f}, new float[]{-0.226304f, 1.165322f, 0.0457f}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 0.918222f}}, new float[][]{new float[]{1.8599364f, -1.1293817f, 0.2198974f}, new float[]{0.3611914f, 0.6388125f, -6.4E-6f}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0890636f}}),
        /* JADX INFO: Fake field, exist only in values array */
        XYZScaling(new float[][]{new float[]{1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f}}, new float[][]{new float[]{1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f}});

        private final float[][] mFwdMethod;
        private final float[][] mRewMethod;

        eColortempMethod(float[][] fArr, float[][] fArr2) {
            this.mFwdMethod = fArr;
            this.mRewMethod = fArr2;
        }

        public final float[][] getFwdMethod() {
            return this.mFwdMethod;
        }

        public final float[][] getRewMethod() {
            return this.mRewMethod;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    enum eRGBXYZTransform {
        /* JADX INFO: Fake field, exist only in values array */
        sRGB(new float[][]{new float[]{3.24097f, -1.53738f, -0.49861f}, new float[]{-0.96924f, 1.875967f, 0.041555f}, new float[]{0.05563f, -0.20398f, 1.056971f}}, new float[][]{new float[]{0.412391f, 0.357584f, 0.180481f}, new float[]{0.212639f, 0.715169f, 0.072192f}, new float[]{0.019331f, 0.119195f, 0.950532f}}),
        P3(new float[][]{new float[]{2.493497f, -0.93138f, -0.40271f}, new float[]{-0.82949f, 1.762664f, 0.023625f}, new float[]{0.035846f, -0.07617f, 0.956884f}}, new float[][]{new float[]{0.486571f, 0.265668f, 0.198217f}, new float[]{0.228975f, 0.691739f, 0.079287f}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, 0.045113f, 1.043944f}});

        private final float[][] mRGB2XYZ;
        private final float[][] mXYZ2RGB;

        eRGBXYZTransform(float[][] fArr, float[][] fArr2) {
            this.mXYZ2RGB = fArr;
            this.mRGB2XYZ = fArr2;
        }

        public final float[][] execute(float[][] fArr) {
            return ExynosDisplayUtils.matrixMultiplication(ExynosDisplayUtils.matrixMultiplication(this.mXYZ2RGB, fArr), this.mRGB2XYZ);
        }
    }

    public ExynosDisplayColor() {
        boolean equals = "eng".equals(Build.TYPE);
        this.GAMMA_EXT_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_ext";
        this.GAMMA_SYSFS_PATH = "/sys/class/dqe/dqe/gamma";
        this.HSC_SYSFS_PATH = "/sys/class/dqe/dqe/hsc";
        this.CGC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc";
        this.CGC17_IDX_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_idx";
        this.CGC17_ENC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_enc";
        this.CGC17_DEC_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_dec";
        this.CGC17_CON_SYSFS_PATH = "/sys/class/dqe/dqe/cgc17_con";
        this.GAMMA_MATRIX_SYSFS_PATH = "/sys/class/dqe/dqe/gamma_matrix";
        this.LINEAR_MATRIX_SYSFS_PATH = "/sys/class/dqe/dqe/linear_matrix";
        this.CGC_DITHER_SYSFS_PATH = "/sys/class/dqe/dqe/cgc_dither";
        this.DE_SYSFS_PATH = "/sys/class/dqe/dqe/de";
        this.COLORTEMP_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_colortemp.xml";
        this.EYETEMP_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_eyetemp.xml";
        this.BYPASS_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_bypass.xml";
        this.RGBGAIN_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_rgbgain.xml";
        this.SKINCOLOR_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_skincolor.xml";
        this.WHITEPOINT_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_whitepoint.xml";
        this.SHARPNESS_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_sharpness.xml";
        this.COLORMODE_XML_FILE_PATH = "/vendor/etc/dqe/calib_data_colormode0.xml";
        this.EXTENSION_OFF = "0";
        this.EXTENSION_ON = "1";
        this.bIsColortempOn = false;
        this.mColortempConvMethod = 0;
        this.bIsRgbWeightOn = false;
        this.eyetemp_array = null;
        this.gamma_bypass_array = null;
        this.gamma_ext_bypass_array = null;
        this.cgc_dither_array = null;
        this.linear_matrix_bypass_array = null;
        this.gamma_matrix_bypass_array = null;
        this.colortemp_map = new TreeMap();
        this.rgain_array = null;
        this.ggain_array = null;
        this.bgain_array = null;
        this.skincolor_array = null;
        this.sharpness_array = null;
        this.whitepoint_array = null;
        this.hsc_bypass_array = null;
        this.HW_VER_8_0 = "08000000";
        this.mExynosDisplayTune = null;
        this.hw_ver = null;
        File file = new File("/sys/class/dqe/dqe/dqe_ver");
        if (file.exists() && file.isFile()) {
            this.hw_ver = ExynosDisplayUtils.getStringFromFile("/sys/class/dqe/dqe/dqe_ver");
            if (equals) {
                VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("hw_ver: "), this.hw_ver, "ExynosDisplayColor");
            }
        }
    }

    public static float[] T2xy(int i) {
        float pow;
        float f;
        try {
            if (i <= 7000) {
                double d = i;
                pow = ((((float) Math.pow(10.0d, 3.0d)) * 0.09911f) / i) + ((((float) Math.pow(10.0d, 6.0d)) * 2.9678f) / ((float) Math.pow(d, 2.0d))) + ((((float) Math.pow(10.0d, 9.0d)) * (-4.607f)) / ((float) Math.pow(d, 3.0d)));
                f = 0.244063f;
            } else {
                double d2 = i;
                pow = ((((float) Math.pow(10.0d, 3.0d)) * 0.24748f) / i) + ((((float) Math.pow(10.0d, 6.0d)) * 1.9018f) / ((float) Math.pow(d2, 2.0d))) + ((((float) Math.pow(10.0d, 9.0d)) * (-2.0064f)) / ((float) Math.pow(d2, 3.0d)));
                f = 0.23704f;
            }
            float f2 = pow + f;
            return new float[]{f2, ((2.87f * f2) + (((float) Math.pow(f2, 2.0d)) * (-3.0f))) - 0.275f};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static float[][] calcChromaticAdaptation(int i, int i2, eColortempMethod ecolortempmethod) {
        try {
            float[][] fwdMethod = ecolortempmethod.getFwdMethod();
            float[][] rewMethod = ecolortempmethod.getRewMethod();
            float[] T2xy = T2xy(i);
            float[] T2xy2 = T2xy(i2);
            float[] xyY2XYZ = xyY2XYZ(T2xy[0], T2xy[1]);
            float[] xyY2XYZ2 = xyY2XYZ(T2xy2[0], T2xy2[1]);
            float[][] matrixMultiplication = ExynosDisplayUtils.matrixMultiplication(fwdMethod, new float[][]{new float[]{xyY2XYZ[0]}, new float[]{xyY2XYZ[1]}, new float[]{xyY2XYZ[2]}});
            float[][] matrixMultiplication2 = ExynosDisplayUtils.matrixMultiplication(fwdMethod, new float[][]{new float[]{xyY2XYZ2[0]}, new float[]{xyY2XYZ2[1]}, new float[]{xyY2XYZ2[2]}});
            return ExynosDisplayUtils.matrixMultiplication(ExynosDisplayUtils.matrixMultiplication(rewMethod, new float[][]{new float[]{matrixMultiplication2[0][0] / matrixMultiplication[0][0], FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, matrixMultiplication2[1][0] / matrixMultiplication[1][0], FullScreenMagnificationGestureHandler.MAX_SCALE}, new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, matrixMultiplication2[2][0] / matrixMultiplication[2][0]}}), fwdMethod);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static float[] xyY2XYZ(float f, float f2) {
        try {
            return f2 == FullScreenMagnificationGestureHandler.MAX_SCALE ? new float[]{FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE} : new float[]{(f * 1.0f) / f2, 1.0f, (((1.0f - f) - f2) * 1.0f) / f2};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final String getColorModePath(String str) {
        String str2 = this.COLORMODE_XML_FILE_PATH;
        String str3 = null;
        try {
            if (str.equals("bypass")) {
                return this.BYPASS_XML_FILE_PATH;
            }
            String pathWithPanel = ExynosDisplayUtils.getPathWithPanel(str2);
            try {
                String[] parserFactoryXMLAttribute = ExynosDisplayUtils.parserFactoryXMLAttribute(pathWithPanel, str, null, "subxml");
                if (parserFactoryXMLAttribute != null && parserFactoryXMLAttribute.length >= 1) {
                    return str2.substring(0, str2.lastIndexOf(".xml")) + "_" + parserFactoryXMLAttribute[0].split("\\s*,\\s*")[0] + ".xml";
                }
                return pathWithPanel;
            } catch (Exception e) {
                str3 = pathWithPanel;
                e = e;
                e.printStackTrace();
                return str3;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    public final String getColorTempFromXml(int i) {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.colortemp_map.isEmpty()) {
            return null;
        }
        Integer num = (Integer) this.colortemp_map.floorKey(Integer.valueOf(i));
        if (num != null) {
            return (String) this.colortemp_map.get(num);
        }
        Integer num2 = (Integer) this.colortemp_map.ceilingKey(Integer.valueOf(i));
        if (num2 != null) {
            return (String) this.colortemp_map.get(num2);
        }
        Log.d("ExynosDisplayColor", "getColorTempFromXml: failed to find " + i);
        return null;
    }

    public final void loadColorTempXml() {
        String pathWithPanel;
        try {
            if (this.colortemp_map.isEmpty() && (pathWithPanel = ExynosDisplayUtils.getPathWithPanel(this.COLORTEMP_XML_FILE_PATH)) != null) {
                String[] parserXML = ExynosDisplayUtils.parserXML(pathWithPanel, "colortemp", "linear_matrix");
                String[] parserXMLAttribute = ExynosDisplayUtils.parserXMLAttribute(pathWithPanel, "linear_matrix", "temp");
                if (parserXML != null && parserXMLAttribute != null && parserXML.length == parserXMLAttribute.length) {
                    for (int i = 0; i < parserXMLAttribute.length; i++) {
                        this.colortemp_map.put(Integer.valueOf(Integer.parseInt(parserXMLAttribute[i])), parserXML[i]);
                    }
                    return;
                }
                Log.e("ExynosDisplayColor", "loadColorTempXml: invalid data in ".concat(pathWithPanel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setColorTempOn(int i) {
        boolean z = i > 0;
        try {
            this.bIsColortempOn = z;
            if (z) {
                int i2 = i - 1;
                this.mColortempConvMethod = i2;
                if (i2 < eColortempMethod.values().length) {
                    return;
                }
                loadColorTempXml();
                return;
            }
            this.colortemp_map.clear();
            try {
                if (this.linear_matrix_bypass_array == null) {
                    this.linear_matrix_bypass_array = ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "linear_matrix");
                }
                String[] strArr = this.linear_matrix_bypass_array;
                if (strArr == null || strArr.length <= 0) {
                    return;
                }
                ExynosDisplayUtils.sysfsWriteSting(this.LINEAR_MATRIX_SYSFS_PATH, strArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void setColorTempValue(int i, int i2) {
        String colorTempFromXml;
        float[][] fArr;
        try {
            if (this.bIsColortempOn) {
                if (this.mColortempConvMethod < eColortempMethod.values().length) {
                    int i3 = this.mColortempConvMethod;
                    colorTempFromXml = null;
                    try {
                        try {
                            fArr = eRGBXYZTransform.P3.execute(calcChromaticAdaptation(i, i2, i3 < eColortempMethod.values().length ? eColortempMethod.values()[i3] : eColortempMethod.values()[0]));
                        } catch (Exception e) {
                            e.printStackTrace();
                            fArr = null;
                        }
                        colorTempFromXml = ExynosDisplayUtils.toString(fArr);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    colorTempFromXml = getColorTempFromXml(i2);
                }
                if (colorTempFromXml != null) {
                    ExynosDisplayUtils.sysfsWriteSting(this.LINEAR_MATRIX_SYSFS_PATH, colorTempFromXml);
                }
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    public final void setEdgeSharpnessOn(int i) {
        String str;
        String[] strArr;
        try {
            if (i != 0) {
                this.sharpness_array = ExynosDisplayUtils.parserXML(this.SHARPNESS_XML_FILE_PATH, "sharpness", "de");
            } else {
                this.sharpness_array = ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "de");
            }
            strArr = this.sharpness_array;
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        if (strArr != null && strArr.length != 0) {
            str = strArr[0];
            if (str != null) {
                Log.d("ExynosDisplayColor", "setEdgeSharpnessOn()");
                ExynosDisplayUtils.sysfsWriteSting(this.DE_SYSFS_PATH, str);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x006d, code lost:
    
        android.util.Log.d("ExynosDisplayColor", "setGammaBypass()");
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0075, code lost:
    
        if (r1 == null) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0077, code lost:
    
        com.android.server.display.exynos.ExynosDisplayUtils.sysfsWriteSting(r7.CGC_DITHER_SYSFS_PATH, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x007c, code lost:
    
        sysfsWriteGamma(r0, r7.EXTENSION_OFF);
        sysfsWriteGamma(r2, r7.EXTENSION_ON);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0086, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setGammaBypass() {
        /*
            r7 = this;
            java.lang.String r0 = "gamma"
            r1 = 0
            java.lang.String[] r2 = r7.gamma_bypass_array     // Catch: java.lang.Exception -> L29
            java.lang.String r3 = "bypass"
            java.lang.String r4 = r7.BYPASS_XML_FILE_PATH
            r5 = 0
            if (r2 != 0) goto L2d
            java.lang.String[] r2 = com.android.server.display.exynos.ExynosDisplayUtils.parserFactoryXMLText(r5, r5, r4, r3, r0)     // Catch: java.lang.Exception -> L29
            r7.gamma_bypass_array = r2     // Catch: java.lang.Exception -> L29
            r7.gamma_ext_bypass_array = r1     // Catch: java.lang.Exception -> L29
            if (r2 != 0) goto L2d
            r2 = 10
            java.lang.String[] r2 = com.android.server.display.exynos.ExynosDisplayUtils.parserFactoryXMLText(r2, r5, r4, r3, r0)     // Catch: java.lang.Exception -> L29
            r7.gamma_bypass_array = r2     // Catch: java.lang.Exception -> L29
            r2 = 8
            java.lang.String[] r0 = com.android.server.display.exynos.ExynosDisplayUtils.parserFactoryXMLText(r2, r5, r4, r3, r0)     // Catch: java.lang.Exception -> L29
            r7.gamma_ext_bypass_array = r0     // Catch: java.lang.Exception -> L29
            goto L2d
        L29:
            r0 = move-exception
            r2 = r1
            r3 = r2
            goto L66
        L2d:
            java.lang.String[] r0 = r7.gamma_bypass_array     // Catch: java.lang.Exception -> L29
            if (r0 == 0) goto L65
            int r0 = r0.length     // Catch: java.lang.Exception -> L29
            if (r0 != 0) goto L35
            goto L65
        L35:
            java.lang.String[] r0 = r7.cgc_dither_array     // Catch: java.lang.Exception -> L29
            if (r0 != 0) goto L42
            java.lang.String r0 = "cgc_dither"
            java.lang.String[] r0 = com.android.server.display.exynos.ExynosDisplayUtils.parserXML(r4, r3, r0)     // Catch: java.lang.Exception -> L29
            r7.cgc_dither_array = r0     // Catch: java.lang.Exception -> L29
        L42:
            java.lang.String[] r0 = r7.gamma_bypass_array     // Catch: java.lang.Exception -> L29
            r0 = r0[r5]     // Catch: java.lang.Exception -> L29
            java.lang.String[] r2 = r7.gamma_ext_bypass_array     // Catch: java.lang.Exception -> L4d
            if (r2 == 0) goto L53
            r2 = r2[r5]     // Catch: java.lang.Exception -> L4d
            goto L54
        L4d:
            r2 = move-exception
            r3 = r1
            r6 = r2
            r2 = r0
            r0 = r6
            goto L66
        L53:
            r2 = r1
        L54:
            java.lang.String[] r3 = r7.cgc_dither_array     // Catch: java.lang.Exception -> L5f
            if (r3 == 0) goto L6b
            int r4 = r3.length     // Catch: java.lang.Exception -> L5f
            if (r4 != 0) goto L5c
            goto L6b
        L5c:
            r1 = r3[r5]     // Catch: java.lang.Exception -> L5f
            goto L6b
        L5f:
            r3 = move-exception
            r6 = r2
            r2 = r0
            r0 = r3
            r3 = r6
            goto L66
        L65:
            return
        L66:
            r0.printStackTrace()
            r0 = r2
            r2 = r3
        L6b:
            if (r0 == 0) goto L86
            java.lang.String r3 = "ExynosDisplayColor"
            java.lang.String r4 = "setGammaBypass()"
            android.util.Log.d(r3, r4)
            if (r1 == 0) goto L7c
            java.lang.String r3 = r7.CGC_DITHER_SYSFS_PATH
            com.android.server.display.exynos.ExynosDisplayUtils.sysfsWriteSting(r3, r1)
        L7c:
            java.lang.String r1 = r7.EXTENSION_OFF
            r7.sysfsWriteGamma(r0, r1)
            java.lang.String r0 = r7.EXTENSION_ON
            r7.sysfsWriteGamma(r2, r0)
        L86:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.exynos.ExynosDisplayColor.setGammaBypass():void");
    }

    public final void setHsvGainOn() {
        String str;
        String[] strArr;
        try {
            if (this.hsc_bypass_array == null) {
                this.hsc_bypass_array = ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "hsc");
            }
            strArr = this.hsc_bypass_array;
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        if (strArr != null && strArr.length != 0) {
            str = strArr[0];
            if (str != null) {
                Log.d("ExynosDisplayColor", "setHsvGainOn()");
                ExynosDisplayUtils.sysfsWriteSting(this.HSC_SYSFS_PATH, str);
            }
        }
    }

    public final void setHsvGainValue(int i, int i2, int i3) {
        String[] strArr;
        StringBuilder sb = new StringBuilder();
        String str = null;
        try {
            strArr = this.hsc_bypass_array;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (strArr != null && strArr.length != 0) {
            String[] split = strArr[0].split(",");
            String str2 = this.hw_ver;
            if (str2 == null) {
                split[9] = Integer.toString(1);
                split[10] = Integer.toString(1);
                split[11] = Integer.toString(1);
                split[12] = Integer.toString(i2 - 127);
                split[13] = Integer.toString(i - 127);
                split[14] = Integer.toString(i3 - 127);
                split[146] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                split[147] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                split[148] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                split[149] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                split[150] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                split[151] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                split[152] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                split[153] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
            } else if (str2.compareTo(this.HW_VER_8_0) >= 0) {
                split[4] = Integer.toString(1);
                split[5] = Integer.toString(i - 127);
                split[6] = Integer.toString(1);
                split[7] = Integer.toString(i2 - 127);
                split[8] = Integer.toString(1);
                split[9] = Integer.toString(i3 - 127);
                for (int i4 = 49; i4 <= 66; i4++) {
                    split[i4] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                }
                split[49] = Integer.toString(0);
                split[58] = Integer.toString(0);
            } else {
                split[8] = Integer.toString(1);
                split[9] = Integer.toString(i - 127);
                split[10] = Integer.toString(1);
                split[11] = Integer.toString(i2 - 127);
                split[12] = Integer.toString(1);
                split[13] = Integer.toString(i3 - 127);
                for (int i5 = 57; i5 <= 74; i5++) {
                    split[i5] = Integer.toString(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT);
                }
                split[57] = Integer.toString(0);
                split[66] = Integer.toString(0);
            }
            for (int i6 = 0; i6 < split.length; i6++) {
                sb.append(i6 < split.length - 1 ? split[i6] + "," : split[i6]);
            }
            if (sb.length() > 0) {
                str = sb.toString();
            }
            if (str != null) {
                Log.d("ExynosDisplayColor", "setHsvGainValue()");
                ExynosDisplayUtils.sysfsWriteSting(this.HSC_SYSFS_PATH, str);
            }
        }
    }

    public final void setRgbGainOn(int i) {
        String str = this.RGBGAIN_XML_FILE_PATH;
        try {
            if (i != 0) {
                this.rgain_array = ExynosDisplayUtils.parserXML(str, "rgbgain", "red");
                this.ggain_array = ExynosDisplayUtils.parserXML(str, "rgbgain", "green");
                this.bgain_array = ExynosDisplayUtils.parserXML(str, "rgbgain", "blue");
            } else {
                this.bgain_array = null;
                this.ggain_array = null;
                this.rgain_array = null;
                setGammaBypass();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setRgbGainValue(int i, int i2, int i3) {
        String[] strArr;
        String[] strArr2;
        try {
            String[] strArr3 = this.rgain_array;
            if (strArr3 == null || strArr3.length == 0 || (strArr = this.ggain_array) == null || strArr.length == 0 || (strArr2 = this.bgain_array) == null || strArr2.length == 0 || i >= strArr3.length || i2 >= strArr.length || i3 >= strArr2.length) {
                return;
            }
            String str = this.rgain_array[i] + "," + this.ggain_array[i2] + "," + this.bgain_array[i3];
            if (str == null) {
                return;
            }
            Log.d("ExynosDisplayColor", "setGammaValue()");
            sysfsWriteGamma(str, this.EXTENSION_OFF);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void setRgbWeightOn(int i) {
        boolean z = i != 0;
        try {
            this.bIsRgbWeightOn = z;
            if (z) {
                return;
            }
            try {
                if (this.gamma_matrix_bypass_array == null) {
                    this.gamma_matrix_bypass_array = ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "gamma_matrix");
                }
                String[] strArr = this.gamma_matrix_bypass_array;
                if (strArr == null || strArr.length <= 0) {
                    return;
                }
                ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_MATRIX_SYSFS_PATH, strArr[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void setSkinColorOn(int i) {
        String str;
        String[] strArr;
        try {
            if (i != 0) {
                this.skincolor_array = ExynosDisplayUtils.parserXML(this.SKINCOLOR_XML_FILE_PATH, "skincolor", "hsc");
            } else {
                this.skincolor_array = ExynosDisplayUtils.parserXML(this.BYPASS_XML_FILE_PATH, "bypass", "hsc");
            }
            strArr = this.skincolor_array;
        } catch (Exception e) {
            e.printStackTrace();
            str = null;
        }
        if (strArr != null && strArr.length != 0) {
            str = strArr[0];
            if (str != null) {
                Log.d("ExynosDisplayColor", "setSkinColorOn()");
                ExynosDisplayUtils.sysfsWriteSting(this.HSC_SYSFS_PATH, str);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x005c, code lost:
    
        android.util.Log.d("ExynosDisplayColor", "setWhitePointColorOn()");
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0062, code lost:
    
        if (r8 == null) goto L33;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0064, code lost:
    
        com.android.server.display.exynos.ExynosDisplayUtils.sysfsWriteSting(r3, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0067, code lost:
    
        com.android.server.display.exynos.ExynosDisplayUtils.sysfsWriteSting(r13.CGC_SYSFS_PATH, r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x008d, code lost:
    
        com.android.server.display.exynos.ExynosDisplayUtils.sysfsWriteSting(r3, r8);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setWhitePointColorOn(int r14) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.display.exynos.ExynosDisplayColor.setWhitePointColorOn(int):void");
    }

    public final void setXMLColorModesImpl(String str) {
        String[] parserFactoryXMLAttribute;
        try {
            String colorModePath = getColorModePath(str);
            try {
                if (!str.equals("bypass") && (parserFactoryXMLAttribute = ExynosDisplayUtils.parserFactoryXMLAttribute(ExynosDisplayUtils.getPathWithPanel(this.COLORMODE_XML_FILE_PATH), str, null, "subxml")) != null) {
                    if (parserFactoryXMLAttribute.length >= 1) {
                        str = "tune";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("ExynosDisplayColor", "setXMLColorModesImpl: xml_path=" + colorModePath + ", mode_name=" + str);
            ExynosDisplayTune exynosDisplayTune = this.mExynosDisplayTune;
            if (exynosDisplayTune != null) {
                exynosDisplayTune.setCalibrationDQE(colorModePath, str);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public final void sysfsWriteGamma(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_EXT_SYSFS_PATH, str2);
        ExynosDisplayUtils.sysfsWriteSting(this.GAMMA_SYSFS_PATH, str);
    }
}
