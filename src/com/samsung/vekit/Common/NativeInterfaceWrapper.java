package com.samsung.vekit.Common;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.Surface;
import com.samsung.vekit.Common.Object.AnalyzeInfo;
import com.samsung.vekit.Common.Object.DoodlePoint;
import com.samsung.vekit.Common.Object.DoodleStroke;
import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Object.ExportInfo;
import com.samsung.vekit.Common.Object.FrcSupportInfo;
import com.samsung.vekit.Common.Object.PVDetectionInfo;
import com.samsung.vekit.Common.Object.PVKeyFrame;
import com.samsung.vekit.Common.Object.PreviewInfo;
import com.samsung.vekit.Common.State.VEKitState;
import com.samsung.vekit.Common.State.VEStateInterface;
import com.samsung.vekit.Common.Type.AnalyzeType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.FrameworkType;
import com.samsung.vekit.Common.Type.SeekType;
import com.samsung.vekit.Common.Type.ViewMode;
import com.samsung.vekit.Control.VEController;
import com.samsung.vekit.External.NativeInterface;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public class NativeInterfaceWrapper {
    private final String TAG = getClass().getSimpleName();
    NativeInterface nativeInterface;
    WeakReference<VEStateInterface> stateInterface;

    public NativeInterfaceWrapper(VEStateInterface vEStateInterface) {
        this.stateInterface = new WeakReference<>(vEStateInterface);
    }

    public synchronized void createFramework(VEController vEController) throws IllegalAccessException {
        VEStateInterface vEStateInterface = this.stateInterface.get();
        if (vEStateInterface.getState() == VEKitState.CREATE) {
            Log.e(this.TAG, "createFramework invalid state. currentState = " + vEStateInterface.getState());
            return;
        }
        if (this.nativeInterface == null) {
            NativeInterface nativeInterface = NativeInterface.getInstance();
            this.nativeInterface = nativeInterface;
            if (nativeInterface == null) {
                Log.e(this.TAG, "Native interface is NULL");
                throw new IllegalAccessException("Native interface is NULL");
            }
        }
        this.nativeInterface.createFramework(vEController);
        vEStateInterface.setState(VEKitState.CREATE);
    }

    public synchronized void initializeFramework(Surface surface, int i, int i2, int i3, int i4, ViewMode viewMode, FrameworkType frameworkType) {
        VEStateInterface vEStateInterface = this.stateInterface.get();
        if (vEStateInterface.getState() != VEKitState.DESTROY && vEStateInterface.getState() != VEKitState.INITIALIZE) {
            this.nativeInterface.initializeFramework(surface, i, i2, i3, i4, viewMode, frameworkType);
            vEStateInterface.setState(VEKitState.INITIALIZE);
            return;
        }
        Log.e(this.TAG, "initializeFramework invalid state. currentState = " + vEStateInterface.getState());
    }

    public synchronized boolean resizeGraphicBuffers(int i, int i2) {
        VEStateInterface vEStateInterface = this.stateInterface.get();
        if (vEStateInterface.getState() != VEKitState.INITIALIZE) {
            Log.e(this.TAG, "resizeGraphicBuffers invalid state. currentState = " + vEStateInterface.getState());
            return false;
        }
        if (i <= 0 || i2 <= 0) {
            Log.e(this.TAG, "resizeGraphicBuffers size should be bigger than 0");
            return false;
        }
        return this.nativeInterface.resizeGraphicBuffers(i, i2);
    }

    public synchronized void finalizeFramework() {
        VEStateInterface vEStateInterface = this.stateInterface.get();
        if (vEStateInterface.getState() != VEKitState.DESTROY && vEStateInterface.getState() != VEKitState.FINALIZE) {
            this.nativeInterface.finalizeFramework();
            vEStateInterface.setState(VEKitState.FINALIZE);
            return;
        }
        Log.e(this.TAG, "finalizeFramework invalid state. currentState = " + vEStateInterface.getState());
    }

    public synchronized void releaseFramework() {
        VEStateInterface vEStateInterface = this.stateInterface.get();
        if (vEStateInterface.getState() == VEKitState.DESTROY) {
            Log.e(this.TAG, "releaseFramework invalid state. currentState = " + vEStateInterface.getState());
            return;
        }
        NativeInterface nativeInterface = this.nativeInterface;
        if (nativeInterface != null) {
            nativeInterface.releaseFramework();
            NativeInterface.releaseInstance(this.nativeInterface);
            this.nativeInterface = null;
        }
        vEStateInterface.setState(VEKitState.DESTROY);
    }

    public void updateViewport(int i, int i2, int i3, int i4) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.updateViewport(i, i2, i3, i4);
                return;
            }
            Log.e(this.TAG, "updateViewport invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void create(Element element) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "create invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.create(element);
        }
    }

    public void update(Element element) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "update invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.update(element);
        }
    }

    public void remove(ElementType elementType, int i) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "remove invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.remove(elementType, i);
        }
    }

    public void attachAnimation(Element element, int i) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attachAnimation invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.attachAnimation(element, i);
        }
    }

    public void detachAnimation(Element element, int i) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "detachAnimation invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.detachAnimation(element, i);
        }
    }

    public void clearAnimations(Element element) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "clearAnimations invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.clearAnimations(element);
        }
    }

    public void cancelAnimation() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.cancelAnimation();
                return;
            }
            Log.e(this.TAG, "cancelAnimation  invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void animate() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.animate();
                return;
            }
            Log.e(this.TAG, "animate  invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void attach(Element element, int i) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attach invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.attach(element, i);
        }
    }

    public void attach(Element element, int i, int i2) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attach invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.attach(element, i, i2);
        }
    }

    public void attach(Element element, ArrayList<Integer> arrayList) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attach invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.attach(element, arrayList);
        }
    }

    public void detach(Element element, int i) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "detach invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.detach(element, i);
        }
    }

    public void clear(Element element) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "clear invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.clear(element);
        }
    }

    public void swap(Element element, int i, int i2) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "swap invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.swap(element, i, i2);
        }
    }

    public void show() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.show();
                return;
            }
            Log.e(this.TAG, "show invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void seekTo(long j, SeekType seekType) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.seekTo(j, seekType);
                return;
            }
            Log.e(this.TAG, "seekTo invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void play() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.play();
                return;
            }
            Log.e(this.TAG, "play invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void pause() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.pause();
                return;
            }
            Log.e(this.TAG, "pause invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void stop() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.stop();
                return;
            }
            Log.e(this.TAG, "stop invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public Bitmap captureAnimatedFrame(Element element, int i, int i2) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.captureAnimatedFrame(element, i, i2);
            }
            Log.e(this.TAG, "captureAnimatedFrame invalid state. currentState = " + vEStateInterface.getState());
            return null;
        }
    }

    public Bitmap captureLatestFrame(int i, int i2) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.captureLatestFrame(i, i2);
            }
            Log.e(this.TAG, "captureLatestFrame invalid state. currentState = " + vEStateInterface.getState());
            return null;
        }
    }

    public Bitmap captureSuperHDRFrame(Element element, int i, int i2, int i3, int i4) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.captureSuperHDRFrame(element, i, i2, i3, i4);
            }
            Log.e(this.TAG, "captureSuperHDRFrame invalid state. currentState = " + vEStateInterface.getState());
            return null;
        }
    }

    public Bitmap captureStaticDoodle(Element element, int i, int i2) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.captureStaticDoodle(element, i, i2);
            }
            Log.e(this.TAG, "captureStaticDoodle invalid state. currentState = " + vEStateInterface.getState());
            return null;
        }
    }

    public long getCurrentMediaPosition() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.getCurrentMediaPosition();
            }
            Log.e(this.TAG, "getCurrentMediaPosition invalid state. currentState = " + vEStateInterface.getState());
            return 0L;
        }
    }

    public void setPreviewInfo(PreviewInfo previewInfo) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "setPreviewInfo invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.setPreviewInfo(previewInfo);
        }
    }

    public void setExportInfo(ExportInfo exportInfo) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "setExportInfo invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.setExportInfo(exportInfo);
        }
    }

    public long pauseExport() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.pauseExport();
            }
            Log.e(this.TAG, "pauseExport invalid state. currentState = " + vEStateInterface.getState());
            return 0L;
        }
    }

    public void resumeExport(long j) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.resumeExport(j);
                return;
            }
            Log.e(this.TAG, "resumeExport invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public long getExportPosition() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                return this.nativeInterface.getExportPosition();
            }
            Log.e(this.TAG, "getExportPosition invalid state. currentState = " + vEStateInterface.getState());
            return 0L;
        }
    }

    public void startDoodle(Element element, DoodleStroke doodleStroke) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.startDoodle(element, doodleStroke);
                return;
            }
            Log.e(this.TAG, "startDoodle invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void drawDoodle(Element element, ArrayList<DoodlePoint> arrayList) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.drawDoodle(element, arrayList);
                return;
            }
            Log.e(this.TAG, "drawDoodle invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void finishDoodle(Element element) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() != VEKitState.FINALIZE && vEStateInterface.getState() != VEKitState.DESTROY) {
                this.nativeInterface.finishDoodle(element);
                return;
            }
            Log.e(this.TAG, "finishDoodle invalid state. currentState = " + vEStateInterface.getState());
        }
    }

    public void attachStroke(Element element, DoodleStroke doodleStroke) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "attachStroke invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.attachStroke(element, doodleStroke);
        }
    }

    public void detachStroke(Element element) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "detachStroke invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.detachStroke(element);
        }
    }

    public void saveDoodle(Element element) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "saveDoodle invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.saveDoodle(element);
        }
    }

    public void loadDoodle(Element element) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "loadDoodle invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.loadDoodle(element);
        }
    }

    public FrcSupportInfo getFrcSupportInfo(int i) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "getFrcSupportInfo invalid state. currentState = " + vEStateInterface.getState());
                return null;
            }
            return this.nativeInterface.getFrcSupportInfo(i);
        }
    }

    public void setAnalyzeInfo(AnalyzeInfo analyzeInfo) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.setAnalyzeInfo(analyzeInfo);
        }
    }

    public void startAnalyze() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.startAnalyze();
        }
    }

    public void stopAnalyze() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.stopAnalyze();
        }
    }

    public long pauseAnalyze() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return 0L;
            }
            return this.nativeInterface.pauseAnalyze();
        }
    }

    public void resumeAnalyze(long j) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.resumeAnalyze(j);
        }
    }

    public void loadAnalyzeSolution(AnalyzeType analyzeType) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.loadAnalyzeSolution(analyzeType);
        }
    }

    public void unloadAnalyzeSolution(AnalyzeType analyzeType) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.unloadAnalyzeSolution(analyzeType);
        }
    }

    public long getCurrentAnalyzedPosition() {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return 0L;
            }
            return this.nativeInterface.getCurrentAnalyzedPosition();
        }
    }

    public void changePortraitVideoFocus(Element element, PVDetectionInfo pVDetectionInfo) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.changePortraitVideoFocus(element, pVDetectionInfo);
        }
    }

    public void changePortraitVideoFocus(Element element, int i, int i2) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.changePortraitVideoFocus(element, i, i2);
        }
    }

    public void changePortraitVideoKeyFrame(Element element, PVKeyFrame pVKeyFrame) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.changePortraitVideoKeyFrame(element, pVKeyFrame);
        }
    }

    public void changePortraitVideoKeyFrameList(Element element, ArrayList<PVKeyFrame> arrayList) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.changePortraitVideoKeyFrameList(element, arrayList);
        }
    }

    public void deletePortraitVideoKeyFrame(Element element, int i) {
        synchronized (this) {
            VEStateInterface vEStateInterface = this.stateInterface.get();
            if (vEStateInterface.getState() == VEKitState.DESTROY) {
                Log.e(this.TAG, "analyze invalid state. currentState = " + vEStateInterface.getState());
                return;
            }
            this.nativeInterface.deletePortraitVideoKeyFrame(element, i);
        }
    }
}
