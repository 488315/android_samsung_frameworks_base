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
public class SemPhotoRemasterManager {
    public static final int PARAMETER_AMOUNT_REMASTER_IMAGES = 1006;
    private static final int PARAMETER_DISABLE_POSTPROCESSING = 1017;
    private static final int PARAMETER_DISABLE_PREPROCESSING = 1016;
    private static final int PARAMETER_ENGINE_VERSION = 1000;
    public static final int PARAMETER_ENHANCEMENT_STRENGTH = 1012;
    public static final int PARAMETER_ENHANCERS_EXCLUDE_LIST = 1009;
    public static final int PARAMETER_ENHANCERS_INCLUDE_LIST = 1013;
    public static final int PARAMETER_ENUM_ENHANCE_TYPE = 2201;
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
    private final Map<Integer, IGetParam> mParamGetterType =
            new HashMap<
                    Integer,
                    IGetParam>() { // from class:
                                   // com.samsung.android.media.photoremaster.SemPhotoRemasterManager.1
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

    private abstract static class IGetParam<T> {
        protected int mID;

        abstract T getParam();

        IGetParam(int id) {
            this.mID = id;
        }
    }

    private static class ILongParamGetter extends IGetParam<String> {
        ILongParamGetter(int id) {
            super(id);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.samsung.android.media.photoremaster.SemPhotoRemasterManager.IGetParam
        public String getParam() {
            return Long.toString(
                    SemPhotoRemasterManager.getEngineInstance().getLongParam(this.mID));
        }
    }

    private static class IStringParamGetter extends IGetParam<String> {
        IStringParamGetter(int id) {
            super(id);
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.samsung.android.media.photoremaster.SemPhotoRemasterManager.IGetParam
        public String getParam() {
            return SemPhotoRemasterManager.getEngineInstance().getStringParam(this.mID);
        }
    }

    private static class IBitmapParamGetter extends IGetParam<Bitmap> {
        IBitmapParamGetter(int id) {
            super(id);
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

    public synchronized void init(Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().init(context);
    }

    public synchronized boolean tryInit(Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        return getEngineInstance().tryInit(context);
    }

    public synchronized void deinit() {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().deinit();
        releaseEngineInstance();
    }

    public void stop() {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().stop();
    }

    public synchronized boolean processImage(int processMode, List<Integer> enhanceModes) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        IDirector director = getEngineInstance();
        if (!(director instanceof ClientRemasterDirector)) {
            LogUtil.w(TAG, "processImage(int, List<Integer>) is not supported below OneUI 4.1");
            return false;
        }
        ClientRemasterDirector remasterDirector = (ClientRemasterDirector) director;
        return remasterDirector.processImage(processMode, enhanceModes);
    }

    public synchronized void setParameter(int id, Object value) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        if (value instanceof Uri) {
            getEngineInstance().setUriParam(id, (Uri) value);
        } else if (value instanceof Bitmap) {
            setParameter(id, (Bitmap) value);
        } else {
            LogUtil.e(
                    TAG,
                    "Failed to setParameter, value not of 'Uri' type or bitmap type: " + value);
        }
    }

    public synchronized void setParameter(int id, Bitmap value) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().setBitmapParam(id, value);
    }

    public synchronized void setParameter(int id, String value) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().setStringParam(id, value);
    }

    public synchronized void setParameter(int id, long value) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        getEngineInstance().setLongParam(id, value);
    }

    public synchronized String getParameter(int id) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        IGetParam paramGetter = this.mParamGetterType.getOrDefault(Integer.valueOf(id), null);
        if (paramGetter != null) {
            Object param = paramGetter.getParam();
            if (param instanceof String) {
                String paramAsString = (String) param;
                return paramAsString;
            }
        }
        return null;
    }

    public synchronized Bitmap getBitmapParameter(int id) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        IGetParam paramGetter = this.mParamGetterType.getOrDefault(Integer.valueOf(id), null);
        if (paramGetter != null) {
            Object param = paramGetter.getParam();
            if (param instanceof Bitmap) {
                Bitmap bitmapParcel = (Bitmap) param;
                return bitmapParcel;
            }
        }
        return null;
    }

    public synchronized void setProgressUpdateListener(final ProgressUpdateListener listener) {
        if (listener == null) {
            getEngineInstance().setProgressUpdateListener(null);
        } else {
            getEngineInstance()
                    .setProgressUpdateListener(
                            new IDirector
                                    .ProgressUpdateListener() { // from class:
                                                                // com.samsung.android.media.photoremaster.SemPhotoRemasterManager.2
                                private final ProgressUpdateListener mListener;

                                {
                                    this.mListener = listener;
                                }

                                @Override // com.samsung.android.photoremaster.IDirector.ProgressUpdateListener
                                public void onUpdateProgress(
                                        double percent,
                                        int currentImageIndex,
                                        int totalImageCount) {
                                    this.mListener.onUpdateProgress(
                                            percent, currentImageIndex, totalImageCount);
                                }

                                @Override // com.samsung.android.photoremaster.IDirector.ProgressUpdateListener
                                public void onUpdateMetadata(String metadata) {
                                    this.mListener.onUpdateMetadata(metadata);
                                }
                            });
        }
    }

    public synchronized String getFocusRoi(String originalImage, String remasteredImage) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called!");
        return getEngineInstance().getFocusRoi(originalImage, remasteredImage);
    }
}
