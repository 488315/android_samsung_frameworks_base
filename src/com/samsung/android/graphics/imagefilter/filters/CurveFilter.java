package com.samsung.android.graphics.imagefilter.filters;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Path;
import android.graphics.RuntimeShader;
import android.graphics.Shader;
import android.util.Log;
import com.samsung.android.graphics.imagefilter.FilterEffect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public final class CurveFilter extends FilterEffect {
    private static final Bitmap.Config CURVE_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int CURVE_TEXTURE_HEIGHT = 1;
    private static final int CURVE_TEXTURE_WIDTH = 256;
    private static final String TAG = "CurveFilter";
    private static final String declareCode = "uniform shader curveTexture;\nuniform float isValid;\n";
    private static final String mainCode = "{\n    vec4 color = sampledColor;\n    if (color.a == 0.0) {\n       return color;\n    };\n    if (isValid != 1.0) {\n       sampledColor = half4(color.r, color.g, color.b, sampledColor.a);\n    } else {\n       float r = curveTexture.eval(vec2(color.r*255, 0.5)).r;\n       float g = curveTexture.eval(vec2(color.g*255, 0.5)).g;\n       float b = curveTexture.eval(vec2(color.b*255, 0.5)).b;\n       sampledColor = half4(r, g, b, sampledColor.a);\n    };\n}\n";
    private final int[] bezierBuffer;
    private BitmapCache bitmapCache;
    private float curveLevel;
    private float maxX;
    private float maxY;
    private float minX;
    private float minY;
    private final RuntimeShader shader;

    private static class Point {
        float x;
        float y;

        public Point(float f, float f2) {
            this.x = f;
            this.y = f2;
        }
    }

    private static class BitmapEntry {
        final Bitmap bitmap;
        final BitmapCache.IntKey key;
        final BitmapShader shader;

        BitmapEntry(BitmapCache.IntKey intKey, Bitmap bitmap, BitmapShader bitmapShader) {
            this.key = intKey;
            this.bitmap = bitmap;
            this.shader = bitmapShader;
        }
    }

    private static class BitmapCache {
        private static final int MAX_SIZE = 30;
        private final LinkedHashMap<IntKey, BitmapEntry> cache;

        private BitmapCache() {
            this.cache = new LinkedHashMap<IntKey, BitmapEntry>(30, 0.75f, true) { // from class: com.samsung.android.graphics.imagefilter.filters.CurveFilter.BitmapCache.1
                @Override // java.util.LinkedHashMap
                protected boolean removeEldestEntry(Map.Entry<IntKey, BitmapEntry> entry) {
                    if (size() <= 30) {
                        return false;
                    }
                    entry.getValue().bitmap.recycle();
                    return true;
                }
            };
        }

        public BitmapEntry get(float f, float f2, float f3, float f4, float f5) {
            return this.cache.get(new IntKey(f, f2, f3, f4, f5));
        }

        public void put(float f, float f2, float f3, float f4, float f5, Bitmap bitmap) {
            IntKey intKey = new IntKey(f, f2, f3, f4, f5);
            this.cache.put(intKey, new BitmapEntry(intKey, bitmap, new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)));
        }

        public boolean contains(float f, float f2, float f3, float f4, float f5) {
            return this.cache.containsKey(new IntKey(f, f2, f3, f4, f5));
        }

        public void clear() {
            Iterator<BitmapEntry> it = this.cache.values().iterator();
            while (it.hasNext()) {
                it.next().bitmap.recycle();
            }
            this.cache.clear();
        }

        public int getSize() {
            return this.cache.size();
        }

        private static class IntKey {
            final int i1;
            final int i2;
            final int i3;
            final int i4;
            final int i5;

            IntKey(float f, float f2, float f3, float f4, float f5) {
                this.i1 = (int) (f * 100.0f);
                this.i2 = (int) (f2 * 100.0f);
                this.i3 = (int) (f3 * 100.0f);
                this.i4 = (int) (f4 * 100.0f);
                this.i5 = (int) (f5 * 100.0f);
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof IntKey)) {
                    return false;
                }
                IntKey intKey = (IntKey) obj;
                return this.i1 == intKey.i1 && this.i2 == intKey.i2 && this.i3 == intKey.i3 && this.i4 == intKey.i4 && this.i5 == intKey.i5;
            }

            public int hashCode() {
                return (((((((this.i1 * 31) + this.i2) * 31) + this.i3) * 31) + this.i4) * 31) + this.i5;
            }
        }
    }

    public CurveFilter() {
        setFilterType(2);
        this.shader = new RuntimeShader(assembleShaderCode("", declareCode, mainCode));
        this.bezierBuffer = new int[256];
        this.bitmapCache = new BitmapCache();
        this.curveLevel = 0.0f;
        this.minX = 0.0f;
        this.minY = 0.0f;
        this.maxX = 255.0f;
        this.maxY = 255.0f;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void clear() {
        this.bitmapCache.clear();
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public String getMainShaderCode() {
        return mainCode;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public String getDeclareShaderCode() {
        return declareCode;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void setParam(int i, float f) {
        if (i == 2) {
            this.curveLevel = f;
            return;
        }
        if (i == 3) {
            this.maxX = f;
            return;
        }
        if (i == 4) {
            this.minX = f;
        } else if (i == 5) {
            this.maxY = f;
        } else {
            if (i != 6) {
                return;
            }
            this.minY = f;
        }
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public float getParam(int i) {
        if (i == 2) {
            return this.curveLevel;
        }
        if (i == 3) {
            return this.maxX;
        }
        if (i == 4) {
            return this.minX;
        }
        if (i == 5) {
            return this.maxY;
        }
        if (i != 6) {
            return -1.0f;
        }
        return this.minY;
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public void updateShader(RuntimeShader runtimeShader) {
        boolean z;
        if (!this.bitmapCache.contains(this.minX, this.minY, this.maxX, this.maxY, this.curveLevel)) {
            Bitmap.Config config = CURVE_CONFIG;
            Bitmap bitmapCreateCurveBitmap = createCurveBitmap(256, 1, config);
            if (isValidBitmap(bitmapCreateCurveBitmap, 256, 1, config)) {
                this.bitmapCache.put(this.minX, this.minY, this.maxX, this.maxY, this.curveLevel, bitmapCreateCurveBitmap);
            } else if (bitmapCreateCurveBitmap != null) {
                bitmapCreateCurveBitmap.recycle();
            }
        }
        BitmapEntry bitmapEntry = this.bitmapCache.get(this.minX, this.minY, this.maxX, this.maxY, this.curveLevel);
        if (bitmapEntry != null && isValidBitmap(bitmapEntry.bitmap, 256, 1, CURVE_CONFIG)) {
            runtimeShader.setInputShader("curveTexture", bitmapEntry.shader);
            z = true;
        } else {
            Log.w(TAG, "mapping shader is null");
            z = false;
        }
        runtimeShader.setFloatUniform("isValid", z ? 1.0f : 0.0f);
    }

    @Override // com.samsung.android.graphics.imagefilter.FilterEffect
    public RuntimeShader getShader() {
        return this.shader;
    }

    private Bitmap createCurveBitmap(int i, int i2, Bitmap.Config config) {
        setBezierBuffer();
        return Bitmap.createBitmap(this.bezierBuffer, i, i2, config).copy(config, true);
    }

    private boolean isValidBitmap(Bitmap bitmap, int i, int i2, Bitmap.Config config) {
        return bitmap != null && !bitmap.isRecycled() && bitmap.getWidth() == i && bitmap.getHeight() == i2 && bitmap.getConfig() == config;
    }

    private void setBezierBuffer() {
        int length = this.bezierBuffer.length;
        float f = length;
        float f2 = 1.0f / f;
        Map.Entry<float[], Integer> controlPoints = getControlPoints();
        float[] key = controlPoints.getKey();
        Integer value = controlPoints.getValue();
        ArrayList<Point> arrayList = new ArrayList<>();
        float[] fArr = new float[key.length];
        for (float f3 = 0.0f; f3 < 1.0d; f3 += f2) {
            for (int i = 0; i < value.intValue() * 2; i++) {
                fArr[i] = key[i];
            }
            for (int iIntValue = value.intValue() - 1; iIntValue > 0; iIntValue--) {
                for (int i2 = 0; i2 < iIntValue; i2++) {
                    int i3 = i2 * 2;
                    float f4 = fArr[i3];
                    fArr[i3] = f4 + ((fArr[i3 + 2] - f4) * f3);
                    int i4 = i3 + 1;
                    float f5 = fArr[i4];
                    fArr[i4] = f5 + ((fArr[i3 + 3] - f5) * f3);
                }
            }
            arrayList.add(new Point(fArr[0] / f, fArr[1] / f));
        }
        arrayList.get(arrayList.size() - 1).x = 1.0f;
        arrayList.get(0).x = 0.0f;
        arrayList.get(0).y = arrayList.get(1).y;
        for (int i5 = 0; i5 < length; i5++) {
            float f6 = i5 / f;
            if (f6 == 0.0f) {
                f6 += 0.001f;
            }
            int iFindLowerIndex = findLowerIndex(arrayList, f6);
            Point point = arrayList.get(iFindLowerIndex);
            Point point2 = arrayList.get(iFindLowerIndex + 1);
            float f7 = point2.x - point.x;
            int iMax = Math.max(0, Math.min(255, Math.round((point.y + ((point2.y - point.y) * (f7 > 0.0f ? (f6 - point.x) / f7 : 0.0f))) * 255.0f))) & 255;
            this.bezierBuffer[i5] = iMax | (iMax << 16) | (-16777216) | (iMax << 8);
        }
    }

    private int findLowerIndex(ArrayList<Point> arrayList, float f) {
        int size = arrayList.size() - 2;
        int i = 0;
        if (f <= arrayList.get(0).x) {
            return 0;
        }
        while (i <= size) {
            int i2 = (i + size) >>> 1;
            float f2 = arrayList.get(i2).x;
            int i3 = i2 + 1;
            float f3 = arrayList.get(i3).x;
            if (f2 <= f && f < f3) {
                return i2;
            }
            if (f < f2) {
                size = i2 - 1;
            } else {
                i = i3;
            }
        }
        return arrayList.size() - 2;
    }

    private Map.Entry<float[], Integer> getControlPoints() {
        int i;
        if (this.minX > this.maxX || this.minY > this.maxY) {
            Log.w(TAG, "Must minX < maxX, minY < maxY " + this.minX + "<" + this.maxX + ", " + this.minY + "<" + this.maxY);
        }
        float f = this.maxX - this.minX;
        float f2 = f / 120.0f;
        float fMin = Math.min(f, this.maxY - this.minY);
        float fMax = Math.max((fMin < 30.0f ? fMin / 10.0f : (fMin / 30.0f) + 2.0f) - 1.0f, 0.0f);
        float f3 = this.curveLevel;
        float f4 = (((fMax * (f3 > 0.0f ? f3 : -f3)) * 2.0f) / 120.0f) + 1.0f;
        float[] fArr = new float[12];
        float f5 = this.minX;
        fArr[0] = f5;
        float f6 = this.minY;
        fArr[1] = f6;
        if (f3 == 0.0f) {
            fArr[2] = this.maxX;
            fArr[3] = this.maxY;
            i = 2;
        } else {
            float f7 = this.maxX;
            fArr[10] = f7;
            float f8 = this.maxY;
            fArr[11] = f8;
            if (f3 > 0.0f) {
                float f9 = f7 - (f2 * f3);
                fArr[8] = f9;
                fArr[9] = f8;
                float f10 = (f9 - ((f9 - f5) / 3.0f)) - f4;
                fArr[4] = f10;
                fArr[5] = (f8 - ((f8 - f6) / 3.0f)) + f4;
                float f11 = (fMin / 255.0f) * f4;
                fArr[6] = f10 + (((f9 - f10) * f3) / 120.0f) + (2.0f * f11 * Math.min((-(f3 - 70.0f)) / 25.0f, f3 / 45.0f));
                float f12 = fArr[5];
                float f13 = fArr[9] - f12;
                float f14 = this.curveLevel;
                fArr[7] = (f12 + (((f13 * f14) * 1.25f) / 120.0f)) - (f11 * Float.max((100.0f / f14) * (-1.0f), Math.min((-(f14 - 70.0f)) / 15.0f, f14 / 55.0f)));
                float f15 = fArr[0];
                fArr[2] = ((f15 + ((fArr[8] - f15) / 3.0f)) - f4) - ((fMin / 50.0f) * Float.max(-1.2f, Math.min((-(this.curveLevel - 70.0f)) / 20.0f, 1.0f)));
                float f16 = fArr[1];
                fArr[3] = f16 + ((fArr[9] - f16) / 3.0f) + (f4 / ((this.curveLevel + 100.0f) / 64.0f));
            } else {
                float f17 = f5 - (f2 * f3);
                fArr[2] = f17;
                fArr[3] = f6;
                float f18 = ((f7 - f17) / 3.0f) + f17 + f4;
                fArr[6] = f18;
                fArr[7] = (f6 + ((f8 - f6) / 3.0f)) - f4;
                float f19 = (fMin / 255.0f) * f4;
                fArr[4] = (f18 + (((f18 - f17) * f3) / 120.0f)) - ((2.0f * f19) * Math.min((-((-f3) - 70.0f)) / 25.0f, (-f3) / 45.0f));
                float f20 = fArr[7];
                float f21 = f20 - fArr[3];
                float f22 = this.curveLevel;
                fArr[5] = f20 + (((f21 * f22) * 1.25f) / 120.0f) + (f19 * Float.max((100.0f / (-f22)) * (-1.0f), Math.min((-((-f22) - 70.0f)) / 15.0f, (-f22) / 55.0f)));
                float f23 = fArr[10];
                fArr[8] = (f23 - ((f23 - fArr[2]) / 3.0f)) + f4 + ((fMin / 50.0f) * Float.max(-1.2f, Math.min((-((-this.curveLevel) - 70.0f)) / 20.0f, 1.0f)));
                float f24 = fArr[11];
                fArr[9] = (f24 - ((f24 - fArr[3]) / 3.0f)) - (f4 / (((-this.curveLevel) + 100.0f) / 64.0f));
            }
            i = 6;
        }
        float f25 = fArr[0];
        for (int i2 = 1; i2 < i; i2++) {
            int i3 = i2 * 2;
            if (f25 > fArr[i3]) {
                f25 += 0.001f;
                fArr[i3] = f25;
            }
        }
        return Map.entry(fArr, Integer.valueOf(i));
    }

    private static float clamp(float f, float f2, float f3) {
        return Math.max(f2, Math.min(f3, f));
    }

    private void PrintPath(Path path) {
        float[] fArrApproximate = path.approximate(0.002f);
        int length = fArrApproximate.length / 3;
        float[] fArr = new float[length];
        float[] fArr2 = new float[length];
        int i = 0;
        boolean z = false;
        int i2 = 0;
        for (int i3 = 0; i3 < length; i3++) {
            float f = fArrApproximate[i2];
            int i4 = i2 + 2;
            float f2 = fArrApproximate[i2 + 1];
            i2 += 3;
            float f3 = fArrApproximate[i4];
            fArr[i3] = f2;
            fArr2[i3] = f3;
            if (f == 0.0f && f2 != f2) {
                Log.d(TAG, "fraction : " + f + " prev : 0.0 x : " + f2 + " prevX :" + f2);
                z = true;
            }
            if (f2 < f2) {
                Log.d(TAG, "x : " + f2 + " prevX :" + f2);
                z = true;
            }
        }
        if (z) {
            while (i < length) {
                Log.d(TAG, "mX : " + fArr[i] + " mY : " + fArr2[i]);
                i++;
            }
            return;
        }
        Log.d(TAG, "line");
        while (i < length) {
            Log.d(TAG, i + " X : " + fArr[i] + " Y : " + fArr2[i]);
            i++;
        }
    }
}
