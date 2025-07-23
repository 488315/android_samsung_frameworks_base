package com.google.zxing.common;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class MinimalECIInput implements ECIInput {
    public final int[] bytes;
    public final int fnc1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class InputEdge {
        public final char c;
        public final int cachedTotalSize;
        public final int encoderIndex;
        public final InputEdge previous;

        public /* synthetic */ InputEdge(char c, ECIEncoderSet eCIEncoderSet, int i, InputEdge inputEdge, int i2, int i3) {
            this(c, eCIEncoderSet, i, inputEdge, i2);
        }

        private InputEdge(char c, ECIEncoderSet eCIEncoderSet, int i, InputEdge inputEdge, int i2) {
            int length;
            char c2 = c == i2 ? (char) 1000 : c;
            this.c = c2;
            this.encoderIndex = i;
            this.previous = inputEdge;
            if (c2 == 1000) {
                length = 1;
            } else {
                length = ("" + c).getBytes(eCIEncoderSet.encoders[i].charset()).length;
            }
            length = (inputEdge == null ? 0 : inputEdge.encoderIndex) != i ? length + 3 : length;
            this.cachedTotalSize = inputEdge != null ? length + inputEdge.cachedTotalSize : length;
        }
    }

    public MinimalECIInput(String str, Charset charset, int i) {
        int i2;
        this.fnc1 = i;
        ECIEncoderSet eCIEncoderSet = new ECIEncoderSet(str, charset, i);
        int i3 = 0;
        if (eCIEncoderSet.encoders.length == 1) {
            this.bytes = new int[str.length()];
            while (i3 < this.bytes.length) {
                char charAt = str.charAt(i3);
                int[] iArr = this.bytes;
                if (charAt == i) {
                    charAt = 1000;
                }
                iArr[i3] = charAt;
                i3++;
            }
            return;
        }
        int length = str.length();
        InputEdge[][] inputEdgeArr = (InputEdge[][]) Array.newInstance((Class<?>) InputEdge.class, length + 1, eCIEncoderSet.encoders.length);
        addEdges(str, eCIEncoderSet, inputEdgeArr, 0, null, i);
        for (int i4 = 1; i4 <= length; i4++) {
            for (int i5 = 0; i5 < eCIEncoderSet.encoders.length; i5++) {
                InputEdge inputEdge = inputEdgeArr[i4][i5];
                if (inputEdge != null && i4 < length) {
                    addEdges(str, eCIEncoderSet, inputEdgeArr, i4, inputEdge, i);
                }
            }
            for (int i6 = 0; i6 < eCIEncoderSet.encoders.length; i6++) {
                inputEdgeArr[i4 - 1][i6] = null;
            }
        }
        int i7 = -1;
        int i8 = Integer.MAX_VALUE;
        for (int i9 = 0; i9 < eCIEncoderSet.encoders.length; i9++) {
            InputEdge inputEdge2 = inputEdgeArr[length][i9];
            if (inputEdge2 != null && (i2 = inputEdge2.cachedTotalSize) < i8) {
                i7 = i9;
                i8 = i2;
            }
        }
        if (i7 < 0) {
            throw new IllegalStateException(ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Failed to encode \"", str, "\""));
        }
        ArrayList arrayList = new ArrayList();
        InputEdge inputEdge3 = inputEdgeArr[length][i7];
        while (inputEdge3 != null) {
            int i10 = inputEdge3.encoderIndex;
            char c = inputEdge3.c;
            if (c == 1000) {
                arrayList.add(0, 1000);
            } else {
                byte[] bytes = ("" + c).getBytes(eCIEncoderSet.encoders[i10].charset());
                for (int length2 = bytes.length - 1; length2 >= 0; length2--) {
                    arrayList.add(0, Integer.valueOf(bytes[length2] & 255));
                }
            }
            inputEdge3 = inputEdge3.previous;
            if ((inputEdge3 == null ? 0 : inputEdge3.encoderIndex) != i10) {
                arrayList.add(0, Integer.valueOf(CharacterSetECI.getCharacterSetECI(eCIEncoderSet.encoders[i10].charset()).getValue() + 256));
            }
        }
        int size = arrayList.size();
        int[] iArr2 = new int[size];
        while (i3 < size) {
            iArr2[i3] = ((Integer) arrayList.get(i3)).intValue();
            i3++;
        }
        this.bytes = iArr2;
    }

    public static void addEdges(String str, ECIEncoderSet eCIEncoderSet, InputEdge[][] inputEdgeArr, int i, InputEdge inputEdge, int i2) {
        ECIEncoderSet eCIEncoderSet2;
        InputEdge inputEdge2;
        int i3;
        char charAt = str.charAt(i);
        int length = eCIEncoderSet.encoders.length;
        int i4 = eCIEncoderSet.priorityEncoderIndex;
        if (i4 < 0 || !(charAt == i2 || eCIEncoderSet.canEncode(charAt, i4))) {
            i4 = 0;
        } else {
            length = i4 + 1;
        }
        int i5 = i4;
        while (i5 < length) {
            if (charAt == i2 || eCIEncoderSet.canEncode(charAt, i5)) {
                eCIEncoderSet2 = eCIEncoderSet;
                inputEdge2 = inputEdge;
                i3 = i2;
                InputEdge inputEdge3 = new InputEdge(charAt, eCIEncoderSet2, i5, inputEdge2, i3, 0);
                InputEdge[] inputEdgeArr2 = inputEdgeArr[i + 1];
                int i6 = inputEdge3.encoderIndex;
                InputEdge inputEdge4 = inputEdgeArr2[i6];
                if (inputEdge4 != null) {
                    if (inputEdge4.cachedTotalSize <= inputEdge3.cachedTotalSize) {
                    }
                }
                inputEdgeArr2[i6] = inputEdge3;
            } else {
                eCIEncoderSet2 = eCIEncoderSet;
                inputEdge2 = inputEdge;
                i3 = i2;
            }
            i5++;
            eCIEncoderSet = eCIEncoderSet2;
            inputEdge = inputEdge2;
            i2 = i3;
        }
    }

    @Override // com.google.zxing.common.ECIInput
    public final char charAt(int i) {
        if (i >= 0) {
            int[] iArr = this.bytes;
            if (i < iArr.length) {
                if (isECI(i)) {
                    throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "value at ", " is not a character but an ECI"));
                }
                return (char) (isFNC1(i) ? this.fnc1 : iArr[i]);
            }
        }
        throw new IndexOutOfBoundsException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, ""));
    }

    @Override // com.google.zxing.common.ECIInput
    public final int getECIValue(int i) {
        if (i >= 0) {
            if (i < this.bytes.length) {
                if (isECI(i)) {
                    return r0[i] - 256;
                }
                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "value at ", " is not an ECI but a character"));
            }
        }
        throw new IndexOutOfBoundsException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, ""));
    }

    public final boolean haveNCharacters(int i, int i2) {
        if ((i + i2) - 1 < this.bytes.length) {
            for (int i3 = 0; i3 < i2; i3++) {
                if (!isECI(i + i3)) {
                }
            }
            return true;
        }
        return false;
    }

    @Override // com.google.zxing.common.ECIInput
    public final boolean isECI(int i) {
        if (i >= 0) {
            int[] iArr = this.bytes;
            if (i < iArr.length) {
                int i2 = iArr[i];
                return i2 > 255 && i2 <= 999;
            }
        }
        throw new IndexOutOfBoundsException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, ""));
    }

    public final boolean isFNC1(int i) {
        if (i >= 0) {
            int[] iArr = this.bytes;
            if (i < iArr.length) {
                return iArr[i] == 1000;
            }
        }
        throw new IndexOutOfBoundsException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, ""));
    }

    @Override // com.google.zxing.common.ECIInput
    public final int length() {
        return this.bytes.length;
    }

    @Override // com.google.zxing.common.ECIInput
    public final CharSequence subSequence(int i, int i2) {
        if (i < 0 || i > i2 || i2 > this.bytes.length) {
            throw new IndexOutOfBoundsException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, ""));
        }
        StringBuilder sb = new StringBuilder();
        while (i < i2) {
            if (isECI(i)) {
                throw new IllegalArgumentException(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "value at ", " is not a character but an ECI"));
            }
            sb.append(charAt(i));
            i++;
        }
        return sb;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.bytes.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            if (isECI(i)) {
                sb.append("ECI(");
                sb.append(getECIValue(i));
                sb.append(')');
            } else if (charAt(i) < 128) {
                sb.append('\'');
                sb.append(charAt(i));
                sb.append('\'');
            } else {
                sb.append((int) charAt(i));
            }
        }
        return sb.toString();
    }
}
