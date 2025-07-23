package com.samsung.android.media.photoremaster;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import com.samsung.android.photoremaster.IDirector;
import com.samsung.android.photoremaster.util.LogUtil;
import com.samsung.android.photoremasterservice.ClientRemasterDirector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
/* loaded from: classes6.dex */
public class SemPhotoRemasterManager {
    public static final int PARAMETER_AMOUNT_REMASTER_IMAGES = 1006;
    private static final int PARAMETER_DISABLE_POSTPROCESSING = 1017;
    private static final int PARAMETER_DISABLE_PREPROCESSING = 1016;
    private static final int PARAMETER_ENGINE_VERSION = 1000;
    public static final int PARAMETER_ENHANCEMENT_STRENGTH = 1012;
    public static final int PARAMETER_ENHANCERS_EXCLUDE_LIST = 1009;
    public static final int PARAMETER_ENHANCERS_INCLUDE_LIST = 1013;
    public static final int PARAMETER_ENUM_ENHANCE_TYPE = 2201;
    private static final int PARAMETER_ESTIMATORS_INIT_LIST = 1018;
    private static final int PARAMETER_EXTRA_JSON = 1015;
    public static final int PARAMETER_GIF_SAVE_FORMAT = 1010;
    public static final int PARAMETER_INPUT_BITMAP = 1014;
    public static final int PARAMETER_JPEG_QUALITY = 1008;
    public static final int PARAMETER_LAST_MODIFIED_DATETIME_INPUT = 1004;
    public static final int PARAMETER_OUTPUT_BITMAP = 2203;
    public static final int PARAMETER_PATH_INPUT = 1002;
    public static final int PARAMETER_PATH_RESULT = 1003;
    public static final int PARAMETER_SCENETYPE_INPUT = 1005;
    public static final int PARAMETER_SERVICE_PURPOSE = 1011;
    public static final int PARAMETER_SET_OUTPUT_DIR = 1007;
    public static final int PARAMETER_TAG_ANALYZED_FULL = 2101;
    public static final int PARAMETER_TAG_ENHANCE_TYPE = 2102;
    public static final int PARAMETER_TAG_REVITALIZED = 2103;
    public static final int PARAMETER_TAG_SUGGESTION_ENHANCE_LIST = 2202;
    public static final int PARAMETER_URI_INPUT = 1001;
    private static final String TAG = "SemPhotoRemasterManager";
    private static IDirector sEngineInstance;
    private static final Object sInitDeinitSyncLock = new Object();
    private final Map<Integer, IGetParam> mParamGetterType = new HashMap<Integer, IGetParam>() { // from class: com.samsung.android.media.photoremaster.SemPhotoRemasterManager.1
        {
            put(1000, new IStringParamGetter(1000));
            put(1002, new IStringParamGetter(1002));
            put(1003, new IStringParamGetter(1003));
            put(2101, new IStringParamGetter(2101));
            put(2102, new IStringParamGetter(2102));
            put(2103, new IStringParamGetter(2103));
            put(2202, new IStringParamGetter(2202));
            put(2201, new ILongParamGetter(2201));
            put(1006, new ILongParamGetter(1006));
            put(1008, new ILongParamGetter(1008));
            put(1005, new IStringParamGetter(1005));
            put(2203, new IBitmapParamGetter(2203));
        }
    };

    public interface ProgressUpdateListener {
        void onUpdateMetadata(String str);

        void onUpdateProgress(double d, int i, int i2);
    }

    private static abstract class IGetParam<T> {
        protected int mID;

        abstract T getParam();

        IGetParam(int i) {
            this.mID = i;
        }
    }

    private static class ILongParamGetter extends IGetParam<String> {
        ILongParamGetter(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.samsung.android.media.photoremaster.SemPhotoRemasterManager.IGetParam
        public String getParam() {
            return Long.toString(SemPhotoRemasterManager.getEngineInstance().getLongParam(this.mID));
        }
    }

    private static class IStringParamGetter extends IGetParam<String> {
        IStringParamGetter(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.samsung.android.media.photoremaster.SemPhotoRemasterManager.IGetParam
        public String getParam() {
            return SemPhotoRemasterManager.getEngineInstance().getStringParam(this.mID);
        }
    }

    private static class IBitmapParamGetter extends IGetParam<Bitmap> {
        IBitmapParamGetter(int i) {
            super(i);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.samsung.android.media.photoremaster.SemPhotoRemasterManager.IGetParam
        public Bitmap getParam() {
            return SemPhotoRemasterManager.getEngineInstance().getBitmapParam(this.mID);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static synchronized IDirector getEngineInstance() {
        IDirector iDirector;
        synchronized (SemPhotoRemasterManager.class) {
            if (sEngineInstance == null) {
                LogUtil.d(TAG, "New Instance is created in getEngineInstance");
                sEngineInstance = new ClientRemasterDirector();
            }
            iDirector = sEngineInstance;
        }
        return iDirector;
    }

    private static synchronized void releaseEngineInstance() {
        synchronized (SemPhotoRemasterManager.class) {
            LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
            sEngineInstance = null;
        }
    }

    public SemPhotoRemasterManager() {
        LogUtil.i(TAG, "Constructor is called!");
    }

    public synchronized void init(Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        synchronized (sInitDeinitSyncLock) {
            getEngineInstance().init(context);
        }
    }

    public synchronized boolean tryInit(Context context) {
        boolean tryInit;
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        synchronized (sInitDeinitSyncLock) {
            tryInit = getEngineInstance().tryInit(context);
        }
        return tryInit;
    }

    public synchronized void deinit() {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        synchronized (sInitDeinitSyncLock) {
            getEngineInstance().deinit();
            releaseEngineInstance();
        }
    }

    public void stop() {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().stop();
    }

    public synchronized boolean processImage(int i, List<Integer> list) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        IDirector engineInstance = getEngineInstance();
        if (!(engineInstance instanceof ClientRemasterDirector)) {
            LogUtil.w(TAG, "processImage(int, List<Integer>) is not supported below OneUI 4.1");
            return false;
        }
        return ((ClientRemasterDirector) engineInstance).processImage(i, list);
    }

    public synchronized void setParameter(int i, Object obj) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        if (obj instanceof Uri) {
            getEngineInstance().setUriParam(i, (Uri) obj);
        } else if (obj instanceof Bitmap) {
            setParameter(i, (Bitmap) obj);
        } else {
            LogUtil.e(TAG, "Failed to setParameter, value not of 'Uri' type or bitmap type: " + obj);
        }
    }

    public synchronized void setParameter(int i, Bitmap bitmap) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().setBitmapParam(i, bitmap);
    }

    public synchronized void setParameter(int i, String str) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().setStringParam(i, str);
    }

    public synchronized void setParameter(int i, long j) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().setLongParam(i, j);
    }

    public synchronized String getParameter(int i) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        IGetParam orDefault = this.mParamGetterType.getOrDefault(Integer.valueOf(i), null);
        if (orDefault != null) {
            Object param = orDefault.getParam();
            if (param instanceof String) {
                return (String) param;
            }
        }
        return null;
    }

    public synchronized Bitmap getBitmapParameter(int i) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        IGetParam orDefault = this.mParamGetterType.getOrDefault(Integer.valueOf(i), null);
        if (orDefault != null) {
            Object param = orDefault.getParam();
            if (param instanceof Bitmap) {
                return (Bitmap) param;
            }
        }
        return null;
    }

    public synchronized void setProgressUpdateListener(ProgressUpdateListener progressUpdateListener) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        if (progressUpdateListener == null) {
            getEngineInstance().setProgressUpdateListener(null);
        } else {
            getEngineInstance().setProgressUpdateListener(new IDirector.ProgressUpdateListener(this, progressUpdateListener) { // from class: com.samsung.android.media.photoremaster.SemPhotoRemasterManager.2
                private final ProgressUpdateListener mListener;
                final /* synthetic */ ProgressUpdateListener val$listener;

                {
                    this.val$listener = progressUpdateListener;
                    this.mListener = progressUpdateListener;
                }

                @Override // com.samsung.android.photoremaster.IDirector.ProgressUpdateListener
                public void onUpdateProgress(double d, int i, int i2) {
                    this.mListener.onUpdateProgress(d, i, i2);
                }

                @Override // com.samsung.android.photoremaster.IDirector.ProgressUpdateListener
                public void onUpdateMetadata(String str) {
                    this.mListener.onUpdateMetadata(str);
                }
            });
        }
    }

    public synchronized String getFocusRoi(String str, String str2) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        return getEngineInstance().getFocusRoi(str, str2);
    }
}
