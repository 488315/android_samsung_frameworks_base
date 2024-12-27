package com.android.server.wm;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Pair;
import android.util.Size;
import android.view.InputWindowHandle;
import android.window.ITrustedPresentationListener;
import android.window.TrustedPresentationThresholds;
import android.window.WindowInfosListener;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.utils.RegionUtils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TrustedPresentationListenerController {
    public Handler mHandler;
    public HandlerThread mHandlerThread;
    public InputWindowHandle[] mLastWindowHandles;
    public AnonymousClass1 mWindowInfosListener;
    public final Object mHandlerThreadLock = new Object();
    public final Listeners mRegisteredListeners = new Listeners();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.wm.TrustedPresentationListenerController$1, reason: invalid class name */
    public final class AnonymousClass1 extends WindowInfosListener {
        public AnonymousClass1() {}

        public final void onWindowInfosChanged(
                final InputWindowHandle[] inputWindowHandleArr,
                WindowInfosListener.DisplayInfo[] displayInfoArr) {
            TrustedPresentationListenerController.this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.wm.TrustedPresentationListenerController$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            TrustedPresentationListenerController.AnonymousClass1 anonymousClass1 =
                                    TrustedPresentationListenerController.AnonymousClass1.this;
                            TrustedPresentationListenerController.this.computeTpl(
                                    inputWindowHandleArr);
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Listeners {
        public final ArrayMap mUniqueListeners = new ArrayMap();
        public final ArrayMap mWindowToListeners = new ArrayMap();

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ListenerDeathRecipient implements IBinder.DeathRecipient {
            public int mInstances = 0;
            public final IBinder mListenerBinder;

            public ListenerDeathRecipient(IBinder iBinder) {
                this.mListenerBinder = iBinder;
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                }
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                TrustedPresentationListenerController.this.mHandler.post(
                        new TrustedPresentationListenerController$$ExternalSyntheticLambda2(
                                1, this));
            }
        }

        public Listeners() {}

        public final void removeListeners(IBinder iBinder, Optional optional) {
            for (int size = this.mWindowToListeners.size() - 1; size >= 0; size--) {
                ArrayList arrayList = (ArrayList) this.mWindowToListeners.valueAt(size);
                for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                    TrustedPresentationInfo trustedPresentationInfo =
                            (TrustedPresentationInfo) arrayList.get(size2);
                    if (trustedPresentationInfo.mListener.asBinder() == iBinder) {
                        if (!optional.isEmpty()) {
                            if (trustedPresentationInfo.mId
                                    != ((Integer) optional.get()).intValue()) {}
                        }
                        arrayList.remove(size2);
                    }
                }
                if (arrayList.isEmpty()) {
                    this.mWindowToListeners.removeAt(size);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TrustedPresentationInfo {
        public final int mId;
        public final ITrustedPresentationListener mListener;
        public final TrustedPresentationThresholds mThresholds;
        public boolean mLastComputedTrustedPresentationState = false;
        public boolean mLastReportedTrustedPresentationState = false;
        public long mEnteredTrustedPresentationStateTime = -1;

        public TrustedPresentationInfo(
                TrustedPresentationThresholds trustedPresentationThresholds,
                int i,
                ITrustedPresentationListener iTrustedPresentationListener) {
            this.mThresholds = trustedPresentationThresholds;
            this.mId = i;
            this.mListener = iTrustedPresentationListener;
        }
    }

    public static void addListenerUpdate(
            ArrayMap arrayMap,
            ITrustedPresentationListener iTrustedPresentationListener,
            int i,
            boolean z) {
        Pair pair = (Pair) arrayMap.get(iTrustedPresentationListener);
        if (pair == null) {
            pair = new Pair(new IntArray(), new IntArray());
            arrayMap.put(iTrustedPresentationListener, pair);
        }
        if (z) {
            ((IntArray) pair.first).add(i);
        } else {
            ((IntArray) pair.second).add(i);
        }
    }

    public final void computeTpl(InputWindowHandle[] inputWindowHandleArr) {
        Listeners listeners;
        Rect rect;
        Matrix matrix;
        float[] fArr;
        Region region;
        InputWindowHandle[] inputWindowHandleArr2;
        int i;
        int i2;
        InputWindowHandle inputWindowHandle;
        long j;
        Rect rect2;
        Region region2;
        char c;
        long j2;
        ArrayList arrayList;
        ArrayMap arrayMap;
        float f;
        int i3;
        InputWindowHandle inputWindowHandle2;
        ArrayMap arrayMap2;
        long j3;
        this.mLastWindowHandles = inputWindowHandleArr;
        if (inputWindowHandleArr == null || inputWindowHandleArr.length == 0) {
            return;
        }
        Listeners listeners2 = this.mRegisteredListeners;
        if (listeners2.mWindowToListeners.isEmpty()) {
            return;
        }
        Rect rect3 = new Rect();
        Matrix matrix2 = new Matrix();
        float[] fArr2 = new float[9];
        Region region3 = new Region();
        long currentTimeMillis = System.currentTimeMillis();
        boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_TPL_enabled;
        char c2 = 1;
        if (zArr[1]) {
            ProtoLogImpl_54989576.v(
                    ProtoLogGroup.WM_DEBUG_TPL,
                    6408851516381868623L,
                    1,
                    null,
                    Long.valueOf(this.mLastWindowHandles.length));
        }
        ArrayMap arrayMap3 = new ArrayMap();
        InputWindowHandle[] inputWindowHandleArr3 = this.mLastWindowHandles;
        int length = inputWindowHandleArr3.length;
        int i4 = 0;
        while (i4 < length) {
            InputWindowHandle inputWindowHandle3 = inputWindowHandleArr3[i4];
            if (inputWindowHandle3.canOccludePresentation) {
                rect3.set(inputWindowHandle3.frame);
                ArrayList arrayList2 =
                        (ArrayList)
                                listeners2.mWindowToListeners.get(
                                        inputWindowHandle3.getWindowToken());
                if (arrayList2 != null) {
                    Region region4 = new Region();
                    listeners = listeners2;
                    region4.op(rect3, region3, Region.Op.DIFFERENCE);
                    inputWindowHandle3.transform.invert(matrix2);
                    matrix2.getValues(fArr2);
                    float f2 = fArr2[0];
                    float f3 = fArr2[1];
                    float f4 = (f3 * f3) + (f2 * f2);
                    inputWindowHandleArr2 = inputWindowHandleArr3;
                    i = length;
                    float sqrt = (float) Math.sqrt(f4);
                    float f5 = fArr2[4];
                    float f6 = fArr2[3];
                    float sqrt2 = (float) Math.sqrt((f6 * f6) + (f5 * f5));
                    RectF rectF = new RectF(rect3);
                    matrix = matrix2;
                    Size size = inputWindowHandle3.contentSize;
                    if (zArr[1]) {
                        fArr = fArr2;
                        i2 = i4;
                        rect = rect3;
                        arrayList = arrayList2;
                        arrayMap = arrayMap3;
                        region = region3;
                        j2 = currentTimeMillis;
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_TPL,
                                -5162728346383863020L,
                                640,
                                null,
                                String.valueOf(region4),
                                String.valueOf(rectF),
                                String.valueOf(size),
                                Double.valueOf(sqrt),
                                Double.valueOf(sqrt2));
                    } else {
                        rect = rect3;
                        fArr = fArr2;
                        region = region3;
                        j2 = currentTimeMillis;
                        arrayList = arrayList2;
                        arrayMap = arrayMap3;
                        i2 = i4;
                    }
                    float f7 = -1.0f;
                    if (size.getWidth() != 0
                            && size.getHeight() != 0
                            && rectF.width() != FullScreenMagnificationGestureHandler.MAX_SCALE
                            && rectF.height() != FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        float min = Math.min(sqrt * sqrt2, 1.0f);
                        if (zArr[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_TPL,
                                    898769258643799441L,
                                    2,
                                    null,
                                    Double.valueOf(min));
                        }
                        float height =
                                (rectF.height() / size.getHeight())
                                        * (rectF.width() / size.getWidth())
                                        * min;
                        if (zArr[1]) {
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_TPL,
                                    -455501334697331596L,
                                    2,
                                    null,
                                    Double.valueOf(height));
                        }
                        final float[] fArr3 = new float[1];
                        RegionUtils.forEachRect(
                                region4,
                                new Consumer() { // from class:
                                                 // com.android.server.wm.TrustedPresentationListenerController$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        float[] fArr4 = fArr3;
                                        Rect rect4 = (Rect) obj;
                                        fArr4[0] = fArr4[0] + (rect4.height() * rect4.width());
                                    }
                                });
                        f7 = height * (fArr3[0] / (rectF.height() * rectF.width()));
                    }
                    float f8 = inputWindowHandle3.alpha;
                    if (zArr[1]) {
                        ProtoLogImpl_54989576.v(
                                ProtoLogGroup.WM_DEBUG_TPL,
                                854487339271667012L,
                                26,
                                null,
                                Double.valueOf(f7),
                                Double.valueOf(f8),
                                Long.valueOf(j2));
                    }
                    int i5 = 0;
                    while (i5 < arrayList.size()) {
                        ArrayList arrayList3 = arrayList;
                        TrustedPresentationInfo trustedPresentationInfo =
                                (TrustedPresentationInfo) arrayList3.get(i5);
                        ITrustedPresentationListener iTrustedPresentationListener =
                                trustedPresentationInfo.mListener;
                        boolean z = trustedPresentationInfo.mLastComputedTrustedPresentationState;
                        boolean z2 =
                                f8 >= trustedPresentationInfo.mThresholds.getMinAlpha()
                                        && f7
                                                >= trustedPresentationInfo.mThresholds
                                                        .getMinFractionRendered();
                        trustedPresentationInfo.mLastComputedTrustedPresentationState = z2;
                        if (zArr[1]) {
                            inputWindowHandle2 = inputWindowHandle3;
                            f = f8;
                            i3 = i5;
                            ProtoLogImpl_54989576.v(
                                    ProtoLogGroup.WM_DEBUG_TPL,
                                    -2248576188205088843L,
                                    2720,
                                    null,
                                    String.valueOf(z),
                                    String.valueOf(z2),
                                    Double.valueOf(f8),
                                    Double.valueOf(
                                            trustedPresentationInfo.mThresholds.getMinAlpha()),
                                    Double.valueOf(f7),
                                    Double.valueOf(
                                            trustedPresentationInfo.mThresholds
                                                    .getMinFractionRendered()));
                        } else {
                            f = f8;
                            i3 = i5;
                            inputWindowHandle2 = inputWindowHandle3;
                        }
                        int i6 = trustedPresentationInfo.mId;
                        if (!z || z2) {
                            arrayMap2 = arrayMap;
                            if (!z && z2) {
                                j3 = j2;
                                trustedPresentationInfo.mEnteredTrustedPresentationStateTime = j3;
                                this.mHandler.postDelayed(
                                        new TrustedPresentationListenerController$$ExternalSyntheticLambda2(
                                                0, this),
                                        (long)
                                                (trustedPresentationInfo.mThresholds
                                                                .getStabilityRequirementMillis()
                                                        * 1.5d));
                                if (trustedPresentationInfo.mLastReportedTrustedPresentationState
                                        && z2
                                        && j3
                                                        - trustedPresentationInfo
                                                                .mEnteredTrustedPresentationStateTime
                                                > trustedPresentationInfo.mThresholds
                                                        .getStabilityRequirementMillis()) {
                                    trustedPresentationInfo.mLastReportedTrustedPresentationState =
                                            true;
                                    addListenerUpdate(
                                            arrayMap2, iTrustedPresentationListener, i6, true);
                                    if (zArr[0]) {
                                        ProtoLogImpl_54989576.d(
                                                ProtoLogGroup.WM_DEBUG_TPL,
                                                5405816744363636527L,
                                                4,
                                                null,
                                                String.valueOf(iTrustedPresentationListener),
                                                Long.valueOf(i6));
                                    }
                                }
                                i5 = i3 + 1;
                                arrayMap = arrayMap2;
                                j2 = j3;
                                inputWindowHandle3 = inputWindowHandle2;
                                f8 = f;
                                arrayList = arrayList3;
                            }
                        } else {
                            if (trustedPresentationInfo.mLastReportedTrustedPresentationState) {
                                trustedPresentationInfo.mLastReportedTrustedPresentationState =
                                        false;
                                arrayMap2 = arrayMap;
                                addListenerUpdate(
                                        arrayMap2, iTrustedPresentationListener, i6, false);
                                if (zArr[0]) {
                                    ProtoLogImpl_54989576.d(
                                            ProtoLogGroup.WM_DEBUG_TPL,
                                            6236170793308011579L,
                                            4,
                                            null,
                                            String.valueOf(iTrustedPresentationListener),
                                            Long.valueOf(i6));
                                }
                            } else {
                                arrayMap2 = arrayMap;
                            }
                            trustedPresentationInfo.mEnteredTrustedPresentationStateTime = -1L;
                        }
                        j3 = j2;
                        if (trustedPresentationInfo.mLastReportedTrustedPresentationState) {}
                        i5 = i3 + 1;
                        arrayMap = arrayMap2;
                        j2 = j3;
                        inputWindowHandle3 = inputWindowHandle2;
                        f8 = f;
                        arrayList = arrayList3;
                    }
                    inputWindowHandle = inputWindowHandle3;
                    arrayMap3 = arrayMap;
                    j = j2;
                } else {
                    listeners = listeners2;
                    rect = rect3;
                    matrix = matrix2;
                    fArr = fArr2;
                    region = region3;
                    inputWindowHandleArr2 = inputWindowHandleArr3;
                    i = length;
                    i2 = i4;
                    inputWindowHandle = inputWindowHandle3;
                    j = currentTimeMillis;
                }
                rect2 = rect;
                region2 = region;
                region2.op(rect2, Region.Op.UNION);
                c = 1;
                if (zArr[1]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_TPL,
                            -1135667737459933313L,
                            0,
                            null,
                            String.valueOf(inputWindowHandle.name),
                            String.valueOf(rect2.toShortString()),
                            String.valueOf(region2));
                }
            } else {
                if (zArr[c2]) {
                    ProtoLogImpl_54989576.v(
                            ProtoLogGroup.WM_DEBUG_TPL,
                            7718187745767272532L,
                            0,
                            null,
                            String.valueOf(inputWindowHandle3.name));
                }
                listeners = listeners2;
                matrix = matrix2;
                fArr = fArr2;
                region2 = region3;
                c = c2;
                inputWindowHandleArr2 = inputWindowHandleArr3;
                i = length;
                i2 = i4;
                rect2 = rect3;
                j = currentTimeMillis;
            }
            i4 = i2 + 1;
            c2 = c;
            rect3 = rect2;
            region3 = region2;
            currentTimeMillis = j;
            listeners2 = listeners;
            inputWindowHandleArr3 = inputWindowHandleArr2;
            length = i;
            matrix2 = matrix;
            fArr2 = fArr;
        }
        for (int i7 = 0; i7 < arrayMap3.size(); i7++) {
            Pair pair = (Pair) arrayMap3.valueAt(i7);
            try {
                ((ITrustedPresentationListener) arrayMap3.keyAt(i7))
                        .onTrustedPresentationChanged(
                                ((IntArray) pair.first).toArray(),
                                ((IntArray) pair.second).toArray());
            } catch (RemoteException unused) {
            }
        }
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter,
                        "TrustedPresentationListenerController:",
                        "  Active unique listeners (");
        Listeners listeners = this.mRegisteredListeners;
        m$1.append(listeners.mUniqueListeners.size());
        m$1.append("):");
        printWriter.println(m$1.toString());
        for (int i = 0; i < listeners.mWindowToListeners.size(); i++) {
            printWriter.println("    window=" + listeners.mWindowToListeners.keyAt(i));
            ArrayList arrayList = (ArrayList) listeners.mWindowToListeners.valueAt(i);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                TrustedPresentationInfo trustedPresentationInfo =
                        (TrustedPresentationInfo) arrayList.get(i2);
                printWriter.println(
                        "      listener="
                                + trustedPresentationInfo.mListener.asBinder()
                                + " id="
                                + trustedPresentationInfo.mId
                                + " thresholds="
                                + trustedPresentationInfo.mThresholds);
            }
        }
    }

    public final void startHandlerThreadIfNeeded() {
        synchronized (this.mHandlerThreadLock) {
            try {
                if (this.mHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("WindowInfosListenerForTpl");
                    this.mHandlerThread = handlerThread;
                    handlerThread.start();
                    this.mHandler = new Handler(this.mHandlerThread.getLooper());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
