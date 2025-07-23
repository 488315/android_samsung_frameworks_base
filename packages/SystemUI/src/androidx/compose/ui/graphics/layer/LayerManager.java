package androidx.compose.ui.graphics.layer;

import android.graphics.Canvas;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import androidx.collection.MutableObjectList;
import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import androidx.compose.ui.graphics.CanvasHolder;
import java.util.Locale;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LayerManager {
    public static final Companion Companion = new Companion(null);
    public static final boolean isRobolectric = Intrinsics.areEqual(Build.FINGERPRINT.toLowerCase(Locale.ROOT), "robolectric");
    public final CanvasHolder canvasHolder;
    public ImageReader imageReader;
    public boolean persistenceIterationInProgress;
    public MutableObjectList postponedReleaseRequests;
    public final MutableScatterSet layerSet = ScatterSetKt.mutableScatterSetOf();
    public final Handler handler = Handler.createAsync(Looper.getMainLooper(), new Handler.Callback() { // from class: androidx.compose.ui.graphics.layer.LayerManager$$ExternalSyntheticLambda0
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            boolean z;
            int i;
            char c = 3;
            boolean z2 = true;
            LayerManager layerManager = LayerManager.this;
            MutableScatterSet mutableScatterSet = layerManager.layerSet;
            if (!mutableScatterSet.isNotEmpty() || LayerManager.isRobolectric) {
                return true;
            }
            ImageReader imageReader = layerManager.imageReader;
            if (imageReader == null) {
                imageReader = ImageReader.newInstance(1, 1, 1, 3);
                imageReader.setOnImageAvailableListener(new LayerManager$$ExternalSyntheticLambda1(), layerManager.handler);
                layerManager.imageReader = imageReader;
            }
            Surface surface = imageReader.getSurface();
            LockHardwareCanvasHelper.INSTANCE.getClass();
            Canvas lockHardwareCanvas = surface.lockHardwareCanvas();
            layerManager.persistenceIterationInProgress = true;
            CanvasHolder canvasHolder = layerManager.canvasHolder;
            AndroidCanvas androidCanvas = canvasHolder.androidCanvas;
            Canvas canvas = androidCanvas.internalCanvas;
            androidCanvas.internalCanvas = lockHardwareCanvas;
            lockHardwareCanvas.save();
            int i2 = 0;
            lockHardwareCanvas.clipRect(0, 0, 1, 1);
            Object[] objArr = mutableScatterSet.elements;
            long[] jArr = mutableScatterSet.metadata;
            int length = jArr.length - 2;
            if (length >= 0) {
                int i3 = 0;
                while (true) {
                    long j = jArr[i3];
                    char c2 = c;
                    boolean z3 = z2;
                    if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i4 = 8;
                        int i5 = 8 - ((~(i3 - length)) >>> 31);
                        z = z3;
                        int i6 = i2;
                        while (i6 < i5) {
                            if ((j & 255) < 128) {
                                GraphicsLayer graphicsLayer = (GraphicsLayer) objArr[(i3 << 3) + i6];
                                graphicsLayer.getClass();
                                Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
                                i = i4;
                                boolean isHardwareAccelerated = androidCanvas.internalCanvas.isHardwareAccelerated();
                                GraphicsLayerImpl graphicsLayerImpl = graphicsLayer.impl;
                                if (isHardwareAccelerated) {
                                    if (!graphicsLayerImpl.getHasDisplayList()) {
                                        try {
                                            graphicsLayer.recordInternal();
                                        } catch (Throwable unused) {
                                        }
                                    }
                                    graphicsLayerImpl.draw(androidCanvas);
                                } else {
                                    graphicsLayerImpl.getClass();
                                }
                            } else {
                                i = i4;
                            }
                            j >>= i;
                            i6++;
                            i4 = i;
                        }
                        if (i5 != i4) {
                            break;
                        }
                    } else {
                        z = z3;
                    }
                    if (i3 == length) {
                        break;
                    }
                    i3++;
                    i2 = 0;
                    z2 = z;
                    c = c2;
                }
            } else {
                z = true;
            }
            lockHardwareCanvas.restore();
            canvasHolder.androidCanvas.internalCanvas = canvas;
            layerManager.persistenceIterationInProgress = false;
            MutableObjectList mutableObjectList = layerManager.postponedReleaseRequests;
            if (mutableObjectList != null && mutableObjectList.isNotEmpty()) {
                Object[] objArr2 = mutableObjectList.content;
                int i7 = mutableObjectList._size;
                for (int i8 = 0; i8 < i7; i8++) {
                    layerManager.release((GraphicsLayer) objArr2[i8]);
                }
                mutableObjectList.clear();
            }
            surface.unlockCanvasAndPost(lockHardwareCanvas);
            return z;
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LayerManager(CanvasHolder canvasHolder) {
        this.canvasHolder = canvasHolder;
    }

    public final void release(GraphicsLayer graphicsLayer) {
        if (!this.persistenceIterationInProgress) {
            if (this.layerSet.remove(graphicsLayer)) {
                graphicsLayer.discardDisplayList$ui_graphics_release();
                return;
            }
            return;
        }
        MutableObjectList mutableObjectList = this.postponedReleaseRequests;
        if (mutableObjectList == null) {
            mutableObjectList = new MutableObjectList(0, 1, null);
            this.postponedReleaseRequests = mutableObjectList;
        }
        mutableObjectList.add(graphicsLayer);
    }
}
