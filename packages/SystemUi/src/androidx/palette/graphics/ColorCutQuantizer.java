package androidx.palette.graphics;

import android.graphics.Color;
import androidx.core.graphics.ColorUtils;
import androidx.palette.graphics.Palette;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ColorCutQuantizer {
    public static final C03481 VBOX_COMPARATOR_VOLUME = new Comparator() { // from class: androidx.palette.graphics.ColorCutQuantizer.1
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            Vbox vbox = (Vbox) obj;
            Vbox vbox2 = (Vbox) obj2;
            return (((vbox2.mMaxBlue - vbox2.mMinBlue) + 1) * (((vbox2.mMaxGreen - vbox2.mMinGreen) + 1) * ((vbox2.mMaxRed - vbox2.mMinRed) + 1))) - (((vbox.mMaxBlue - vbox.mMinBlue) + 1) * (((vbox.mMaxGreen - vbox.mMinGreen) + 1) * ((vbox.mMaxRed - vbox.mMinRed) + 1)));
        }
    };
    public final int[] mColors;
    public final Palette.Filter[] mFilters;
    public final int[] mHistogram;
    public final List mQuantizedColors;
    public final float[] mTempHsl = new float[3];

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Vbox {
        public final int mLowerIndex;
        public int mMaxBlue;
        public int mMaxGreen;
        public int mMaxRed;
        public int mMinBlue;
        public int mMinGreen;
        public int mMinRed;
        public int mPopulation;
        public int mUpperIndex;

        public Vbox(int i, int i2) {
            this.mLowerIndex = i;
            this.mUpperIndex = i2;
            fitBox();
        }

        public final void fitBox() {
            ColorCutQuantizer colorCutQuantizer = ColorCutQuantizer.this;
            int[] iArr = colorCutQuantizer.mColors;
            int[] iArr2 = colorCutQuantizer.mHistogram;
            int i = Integer.MAX_VALUE;
            int i2 = Integer.MIN_VALUE;
            int i3 = Integer.MIN_VALUE;
            int i4 = 0;
            int i5 = Integer.MAX_VALUE;
            int i6 = Integer.MAX_VALUE;
            int i7 = Integer.MIN_VALUE;
            for (int i8 = this.mLowerIndex; i8 <= this.mUpperIndex; i8++) {
                int i9 = iArr[i8];
                i4 += iArr2[i9];
                int i10 = (i9 >> 10) & 31;
                int i11 = (i9 >> 5) & 31;
                int i12 = i9 & 31;
                if (i10 > i7) {
                    i7 = i10;
                }
                if (i10 < i) {
                    i = i10;
                }
                if (i11 > i2) {
                    i2 = i11;
                }
                if (i11 < i5) {
                    i5 = i11;
                }
                if (i12 > i3) {
                    i3 = i12;
                }
                if (i12 < i6) {
                    i6 = i12;
                }
            }
            this.mMinRed = i;
            this.mMaxRed = i7;
            this.mMinGreen = i5;
            this.mMaxGreen = i2;
            this.mMinBlue = i6;
            this.mMaxBlue = i3;
            this.mPopulation = i4;
        }
    }

    public ColorCutQuantizer(int[] iArr, int i, Palette.Filter[] filterArr) {
        Vbox vbox;
        this.mFilters = filterArr;
        int[] iArr2 = new int[32768];
        this.mHistogram = iArr2;
        int i2 = 0;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            int modifyWordWidth = modifyWordWidth(Color.blue(i4), 8, 5) | (modifyWordWidth(Color.red(i4), 8, 5) << 10) | (modifyWordWidth(Color.green(i4), 8, 5) << 5);
            iArr[i3] = modifyWordWidth;
            iArr2[modifyWordWidth] = iArr2[modifyWordWidth] + 1;
        }
        int i5 = 0;
        for (int i6 = 0; i6 < 32768; i6++) {
            if (iArr2[i6] > 0) {
                int rgb = Color.rgb(modifyWordWidth((i6 >> 10) & 31, 5, 8), modifyWordWidth((i6 >> 5) & 31, 5, 8), modifyWordWidth(i6 & 31, 5, 8));
                float[] fArr = this.mTempHsl;
                ThreadLocal threadLocal = ColorUtils.TEMP_ARRAY;
                ColorUtils.RGBToHSL(Color.red(rgb), Color.green(rgb), Color.blue(rgb), fArr);
                if (shouldIgnoreColor(fArr)) {
                    iArr2[i6] = 0;
                }
            }
            if (iArr2[i6] > 0) {
                i5++;
            }
        }
        int[] iArr3 = new int[i5];
        this.mColors = iArr3;
        int i7 = 0;
        for (int i8 = 0; i8 < 32768; i8++) {
            if (iArr2[i8] > 0) {
                iArr3[i7] = i8;
                i7++;
            }
        }
        if (i5 <= i) {
            this.mQuantizedColors = new ArrayList();
            while (i2 < i5) {
                int i9 = iArr3[i2];
                ((ArrayList) this.mQuantizedColors).add(new Palette.Swatch(Color.rgb(modifyWordWidth((i9 >> 10) & 31, 5, 8), modifyWordWidth((i9 >> 5) & 31, 5, 8), modifyWordWidth(i9 & 31, 5, 8)), iArr2[i9]));
                i2++;
            }
            return;
        }
        PriorityQueue priorityQueue = new PriorityQueue(i, VBOX_COMPARATOR_VOLUME);
        priorityQueue.offer(new Vbox(0, this.mColors.length - 1));
        while (priorityQueue.size() < i && (vbox = (Vbox) priorityQueue.poll()) != null) {
            int i10 = vbox.mUpperIndex;
            int i11 = vbox.mLowerIndex;
            if (((i10 + 1) - i11 > 1 ? 1 : i2) == 0) {
                break;
            }
            if (((i10 + 1) - i11 > 1 ? 1 : i2) == 0) {
                throw new IllegalStateException("Can not split a box with only 1 color");
            }
            int i12 = vbox.mMaxRed - vbox.mMinRed;
            int i13 = vbox.mMaxGreen - vbox.mMinGreen;
            int i14 = vbox.mMaxBlue - vbox.mMinBlue;
            int i15 = (i12 < i13 || i12 < i14) ? (i13 < i12 || i13 < i14) ? -1 : -2 : -3;
            ColorCutQuantizer colorCutQuantizer = ColorCutQuantizer.this;
            int[] iArr4 = colorCutQuantizer.mColors;
            modifySignificantOctet(i15, i11, i10, iArr4);
            Arrays.sort(iArr4, i11, vbox.mUpperIndex + 1);
            modifySignificantOctet(i15, i11, vbox.mUpperIndex, iArr4);
            int i16 = vbox.mPopulation / 2;
            int i17 = i2;
            int i18 = i11;
            while (true) {
                int i19 = vbox.mUpperIndex;
                if (i18 <= i19) {
                    i17 += colorCutQuantizer.mHistogram[iArr4[i18]];
                    if (i17 >= i16) {
                        i11 = Math.min(i19 - 1, i18);
                        break;
                    }
                    i18++;
                }
            }
            Vbox vbox2 = colorCutQuantizer.new Vbox(i11 + 1, vbox.mUpperIndex);
            vbox.mUpperIndex = i11;
            vbox.fitBox();
            priorityQueue.offer(vbox2);
            priorityQueue.offer(vbox);
            i2 = 0;
        }
        ArrayList arrayList = new ArrayList(priorityQueue.size());
        Iterator it = priorityQueue.iterator();
        while (it.hasNext()) {
            Vbox vbox3 = (Vbox) it.next();
            ColorCutQuantizer colorCutQuantizer2 = ColorCutQuantizer.this;
            int[] iArr5 = colorCutQuantizer2.mColors;
            int i20 = 0;
            int i21 = 0;
            int i22 = 0;
            int i23 = 0;
            for (int i24 = vbox3.mLowerIndex; i24 <= vbox3.mUpperIndex; i24++) {
                int i25 = iArr5[i24];
                int i26 = colorCutQuantizer2.mHistogram[i25];
                i21 += i26;
                i20 += ((i25 >> 10) & 31) * i26;
                i22 += ((i25 >> 5) & 31) * i26;
                i23 += i26 * (i25 & 31);
            }
            float f = i21;
            Palette.Swatch swatch = new Palette.Swatch(Color.rgb(modifyWordWidth(Math.round(i20 / f), 5, 8), modifyWordWidth(Math.round(i22 / f), 5, 8), modifyWordWidth(Math.round(i23 / f), 5, 8)), i21);
            if (!shouldIgnoreColor(swatch.getHsl())) {
                arrayList.add(swatch);
            }
        }
        this.mQuantizedColors = arrayList;
    }

    public static void modifySignificantOctet(int i, int i2, int i3, int[] iArr) {
        if (i == -2) {
            while (i2 <= i3) {
                int i4 = iArr[i2];
                iArr[i2] = (i4 & 31) | (((i4 >> 5) & 31) << 10) | (((i4 >> 10) & 31) << 5);
                i2++;
            }
            return;
        }
        if (i != -1) {
            return;
        }
        while (i2 <= i3) {
            int i5 = iArr[i2];
            iArr[i2] = ((i5 >> 10) & 31) | ((i5 & 31) << 10) | (((i5 >> 5) & 31) << 5);
            i2++;
        }
    }

    public static int modifyWordWidth(int i, int i2, int i3) {
        return (i3 > i2 ? i << (i3 - i2) : i >> (i2 - i3)) & ((1 << i3) - 1);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0051 A[LOOP:0: B:6:0x000a->B:25:0x0051, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0050 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldIgnoreColor(float[] fArr) {
        boolean z;
        Palette.Filter[] filterArr = this.mFilters;
        if (filterArr != null && filterArr.length > 0) {
            for (Palette.Filter filter : filterArr) {
                ((Palette.C03491) filter).getClass();
                float f = fArr[2];
                if (!(f >= 0.95f)) {
                    if (!(f <= 0.05f)) {
                        float f2 = fArr[0];
                        if (!(f2 >= 10.0f && f2 <= 37.0f && fArr[1] <= 0.82f)) {
                            z = true;
                            if (z) {
                                return true;
                            }
                        }
                    }
                }
                z = false;
                if (z) {
                }
            }
        }
        return false;
    }
}
