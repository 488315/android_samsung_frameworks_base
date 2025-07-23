package com.android.internal.util;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import java.util.Arrays;

/* loaded from: classes4.dex */
public class ExponentiallyBucketedHistogram {
    private final int[] mData;

    public ExponentiallyBucketedHistogram(int i) {
        this.mData = new int[Preconditions.checkArgumentInRange(i, 1, 31, "numBuckets")];
    }

    public void add(int i) {
        if (i <= 0) {
            int[] iArr = this.mData;
            iArr[0] = iArr[0] + 1;
        } else {
            int[] iArr2 = this.mData;
            int min = Math.min(iArr2.length - 1, 32 - Integer.numberOfLeadingZeros(i));
            iArr2[min] = iArr2[min] + 1;
        }
    }

    public void reset() {
        Arrays.fill(this.mData, 0);
    }

    public void log(String str, CharSequence charSequence) {
        StringBuilder sb = new StringBuilder(charSequence);
        sb.append('[');
        for (int i = 0; i < this.mData.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            if (i < this.mData.length - 1) {
                sb.append("<");
                sb.append(1 << i);
            } else {
                sb.append(">=");
                sb.append(1 << (i - 1));
            }
            sb.append(": ");
            sb.append(this.mData[i]);
        }
        sb.append(NavigationBarInflaterView.SIZE_MOD_END);
        Log.d(str, sb.toString());
    }
}
