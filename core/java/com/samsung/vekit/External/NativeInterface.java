package com.samsung.vekit.External;

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
import com.samsung.vekit.Common.Type.AnalyzeType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.Type.FrameworkType;
import com.samsung.vekit.Common.Type.SeekType;
import com.samsung.vekit.Common.Type.ViewMode;
import com.samsung.vekit.Control.VEController;

import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes6.dex */
public class NativeInterface {
    private static final int MAX_ALLOWED_INSTANCES = 1;
    private static Integer sInstanceIdCnt = 0;
    private static final HashMap<Integer, NativeInterface> sInstances = new HashMap<>(1);
    private final int mInstanceId;

    public native void animate();

    public native void attach(Element element, int i);

    public native void attach(Element element, int i, int i2);

    public native void attach(Element element, ArrayList<Integer> arrayList);

    public native void attachAnimation(Element element, int i);

    public native void attachStroke(Element element, DoodleStroke doodleStroke);

    public native void cancelAnimation();

    public native Bitmap captureAnimatedFrame(Element element, int i, int i2);

    public native Bitmap captureLatestFrame(int i, int i2);

    public native Bitmap captureStaticDoodle(Element element, int i, int i2);

    public native Bitmap captureSuperHDRFrame(Element element, int i, int i2, int i3, int i4);

    public native void changePortraitVideoFocus(Element element, int i, int i2);

    public native void changePortraitVideoFocus(Element element, PVDetectionInfo pVDetectionInfo);

    public native void changePortraitVideoKeyFrame(Element element, PVKeyFrame pVKeyFrame);

    public native void changePortraitVideoKeyFrameList(
            Element element, ArrayList<PVKeyFrame> arrayList);

    public native void clear(Element element);

    public native void clearAnimations(Element element);

    public native void create(Element element);

    public native void createFramework(VEController vEController);

    public native void deletePortraitVideoKeyFrame(Element element, int i);

    public native void detach(Element element, int i);

    public native void detachAnimation(Element element, int i);

    public native void detachStroke(Element element);

    public native void drawDoodle(Element element, ArrayList<DoodlePoint> arrayList);

    public native void finalizeFramework();

    public native void finishDoodle(Element element);

    public native long getCurrentAnalyzedPosition();

    public native long getCurrentMediaPosition();

    public native long getExportPosition();

    public native FrcSupportInfo getFrcSupportInfo(int i);

    public native void initializeFramework(
            Surface surface,
            int i,
            int i2,
            int i3,
            int i4,
            ViewMode viewMode,
            FrameworkType frameworkType);

    public native void loadAnalyzeSolution(AnalyzeType analyzeType);

    public native void loadDoodle(Element element);

    public native void pause();

    public native long pauseAnalyze();

    public native long pauseExport();

    public native void play();

    public native void releaseFramework();

    public native void remove(ElementType elementType, int i);

    public native void resumeAnalyze(long j);

    public native void resumeExport(long j);

    public native void saveDoodle(Element element);

    public native void seekTo(long j, SeekType seekType);

    public native void setAnalyzeInfo(AnalyzeInfo analyzeInfo);

    public native void setExportInfo(ExportInfo exportInfo);

    public native void setPreviewInfo(PreviewInfo previewInfo);

    public native void show();

    public native void startAnalyze();

    public native void startDoodle(Element element, DoodleStroke doodleStroke);

    public native void stop();

    public native void stopAnalyze();

    public native void swap(Element element, int i, int i2);

    public native void unloadAnalyzeSolution(AnalyzeType analyzeType);

    public native void update(Element element);

    public native void updateViewport(int i, int i2, int i3, int i4);

    static {
        new NativeLibSetup().init();
    }

    private NativeInterface(int id) {
        this.mInstanceId = id;
    }

    public static synchronized NativeInterface getInstance() {
        synchronized (NativeInterface.class) {
            int size = sInstances.size();
            if (size < 1) {
                sInstances.put(sInstanceIdCnt, new NativeInterface(sInstanceIdCnt.intValue()));
                HashMap<Integer, NativeInterface> hashMap = sInstances;
                Integer num = sInstanceIdCnt;
                sInstanceIdCnt = Integer.valueOf(sInstanceIdCnt.intValue() + 1);
                return hashMap.get(num);
            }
            Log.e(
                    "NativeInterface",
                    "ERROR already Max native interface instances(" + size + ") running");
            return null;
        }
    }

    public static synchronized void releaseInstance(NativeInterface nativeInterface) {
        synchronized (NativeInterface.class) {
            if (nativeInterface != null) {
                sInstances.remove(Integer.valueOf(nativeInterface.mInstanceId));
            }
        }
    }
}
