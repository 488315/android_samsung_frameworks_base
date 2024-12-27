package com.samsung.android.media.photoremaster;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;

import com.samsung.android.photoremaster.IDirector;
import com.samsung.android.photoremaster.util.LogUtil;
import com.samsung.android.photoremasterservice.ClientRemasterDirector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SemPhotoRemaster {
    private static final int END_TO_END = 0;
    private static final int ENHANCE_AND_SAVE = 2;
    private static final int FIND_ENHANCEMENT_TYPE = 1;
    private static final int RUN_O3DP_ENGINE = 2;
    private static final String TAG = "PhotoRemaster";
    private static final Object mStopLock = new Object();
    private final Builder mBuilder;
    private ClientRemasterDirector mServiceClient;

    public interface ProgressUpdateListener {
        void onUpdateMetadata(String str);

        void onUpdateProgress(double d, int i, int i2);
    }

    public static class Builder {
        public static final int ID_AMOUNT_REMASTER_IMAGES = 1006;
        public static final int ID_ENHANCEMENT_STRENGTH = 1012;
        public static final int ID_ENHANCERS_EXCLUDE_LIST = 1009;
        public static final int ID_ENHANCERS_INCLUDE_LIST = 1013;
        public static final int ID_GIF_SAVE_FORMAT = 1010;
        private static final int ID_INPUT_BITMAP = 1014;
        public static final int ID_JPEG_QUALITY = 1008;
        public static final int ID_LAST_MODIFIED_DATETIME_INPUT = 1004;
        private static final int ID_PATH_INPUT = 1002;
        public static final int ID_SCENETYPE_INPUT = 1005;
        public static final int ID_SERVICE_PURPOSE = 1011;
        public static final int ID_SET_OUTPUT_DIR = 1007;
        private static final int ID_URI_INPUT = 1001;
        private static final String TAG = "PhotoRemaster.Builder";
        private final Bitmap mBitmap;
        private String mInputPathName;
        private final Uri mInputUri;
        private ProgressUpdateListener mListener;
        private final List<Pair<Integer, String>> mStringParams = new ArrayList();
        private final List<Pair<Integer, Long>> mLongParams = new ArrayList();
        private boolean mTryInit = false;
        private boolean mRequestFocusRoi = false;

        @Retention(RetentionPolicy.SOURCE)
        public @interface setLongParamaterIds {}

        @Retention(RetentionPolicy.SOURCE)
        public @interface setStringParameterIds {}

        private Builder(Uri inputUri) {
            LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
            LogUtil.d(TAG, "Input file: " + inputUri);
            this.mInputUri = inputUri;
            setInputPathName(inputUri.getPath());
            this.mBitmap = null;
        }

        private Builder(Bitmap inputImage) {
            LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
            LogUtil.d(TAG, "Input is bitmap");
            this.mInputUri = null;
            this.mInputPathName = null;
            this.mBitmap = inputImage;
        }

        public static Builder with(Uri inputImage) {
            return new Builder(inputImage);
        }

        public static Builder with(Bitmap inputImage) {
            return new Builder(inputImage);
        }

        public Builder setParameter(int id, String value) {
            this.mStringParams.add(new Pair<>(Integer.valueOf(id), value));
            return this;
        }

        public Builder setParameter(int id, long value) {
            this.mLongParams.add(new Pair<>(Integer.valueOf(id), Long.valueOf(value)));
            return this;
        }

        public Builder setRequestFocusRoi() {
            if (this.mInputUri == null) {
                throw new IllegalArgumentException(
                        "FocusView does not support Bitmap-Object-Input");
            }
            this.mRequestFocusRoi = true;
            return this;
        }

        public Builder setProgressUpdateListener(ProgressUpdateListener listener) {
            this.mListener = listener;
            return this;
        }

        public Builder setTryInit() {
            this.mTryInit = true;
            return this;
        }

        public SemPhotoRemaster build() {
            return new SemPhotoRemaster(this);
        }

        private static boolean isValidFilePath(String filePath) {
            File file = new File(filePath);
            return file.exists() && file.isFile();
        }

        private void setInputPathName(String pathName) {
            if (pathName == null || !isValidFilePath(pathName)) {
                String errorMsg = "File does not exist or is inaccessible: " + pathName;
                throw new IllegalArgumentException(errorMsg);
            }
            this.mInputPathName = pathName;
        }

        public void updateInputPath(Context context) {
            if (this.mInputUri == null) {
                return;
            }
            String[] projection = {"_data"};
            Cursor cursor =
                    context.getContentResolver()
                            .query(this.mInputUri, projection, null, null, null);
            try {
                if (cursor == null) {
                    LogUtil.d(TAG, "InputPath is set as inputUri.getPath()");
                } else {
                    int column_index = cursor.getColumnIndexOrThrow("_data");
                    cursor.moveToFirst();
                    LogUtil.i(TAG, "InputPath is replaced with content provider");
                    this.mInputPathName = cursor.getString(column_index);
                    LogUtil.d(TAG, "mInputPathName is updated as " + this.mInputPathName);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
    }

    private SemPhotoRemaster(Builder builder) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        this.mBuilder = builder;
    }

    private void setParameters(Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        if (this.mBuilder.mBitmap != null) {
            this.mServiceClient.setBitmapParam(1014, this.mBuilder.mBitmap);
        } else {
            this.mBuilder.updateInputPath(context);
            if (this.mBuilder.mInputPathName == null || this.mBuilder.mInputUri == null) {
                throw new IllegalArgumentException("InputPath is null");
            }
            this.mServiceClient.setStringParam(1002, this.mBuilder.mInputPathName);
            this.mServiceClient.setUriParam(1001, this.mBuilder.mInputUri);
        }
        for (Pair<Integer, String> stringPair : this.mBuilder.mStringParams) {
            this.mServiceClient.setStringParam(stringPair.first.intValue(), stringPair.second);
        }
        for (Pair<Integer, Long> mLongParams : this.mBuilder.mLongParams) {
            this.mServiceClient.setLongParam(
                    mLongParams.first.intValue(), mLongParams.second.longValue());
        }
        if (this.mBuilder.mBitmap != null) {
            this.mServiceClient.setBitmapParam(1014, this.mBuilder.mBitmap);
        }
        if (this.mBuilder.mListener != null) {
            this.mServiceClient.setProgressUpdateListener(
                    new IDirector
                            .ProgressUpdateListener() { // from class:
                                                        // com.samsung.android.media.photoremaster.SemPhotoRemaster.1
                        private final ProgressUpdateListener mListener;

                        {
                            this.mListener = SemPhotoRemaster.this.mBuilder.mListener;
                        }

                        @Override // com.samsung.android.photoremaster.IDirector.ProgressUpdateListener
                        public void onUpdateProgress(
                                double percent, int currentImageIndex, int totalImageCount) {
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

    enum ResultParam {
        PATH_INPUT(1002, ParamDataType.STRING, false),
        PATH_RESULT(1003, ParamDataType.STRING, false),
        ANALYZED_FULL(2101, ParamDataType.STRING, false),
        ENHANCE_TYPE(2102, ParamDataType.STRING, false),
        REVITALIZED(2103, ParamDataType.STRING, false),
        SCENETYPE_INPUT(1005, ParamDataType.STRING, false),
        SUGGESTION_ENHANCE_LIST(2202, ParamDataType.STRING, false),
        ENUM_ENHANCE_TYPE(2201, ParamDataType.LONG, false),
        AMOUNT_REMASTER_IMAGES(1006, ParamDataType.LONG, false),
        JPEG_QUALITY(1008, ParamDataType.LONG, false),
        GIF_SAVE_FORMAT(1010, ParamDataType.LONG, false),
        FOCUS_ROI(2204, ParamDataType.STRING, true),
        OUTPUT_BITMAP(2203, ParamDataType.EXCEPTIONAL, true);

        private final ParamDataType DATA_TYPE;
        public final int ID;
        private final boolean ONDEMAND;

        enum ParamDataType {
            STRING,
            LONG,
            EXCEPTIONAL
        }

        ResultParam(int id, ParamDataType dataType, boolean ondemand) {
            this.ID = id;
            this.DATA_TYPE = dataType;
            this.ONDEMAND = ondemand;
        }
    }

    public static class Result {
        public static final int ID_ANALYZED_FULL = 2101;
        public static final int ID_ENHANCE_TYPE = 2102;
        public static final int ID_ENUM_ENHANCE_TYPE = 2201;
        public static final int ID_FOCUS_ROI = 2204;
        public static final int ID_PATH_INPUT = 1002;
        public static final int ID_PATH_RESULT = 1003;
        public static final int ID_REVITALIZED = 2103;
        public static final int ID_SCENETYPE_INPUT = 1005;
        public static final int ID_SUGGESTION_ENHANCE_LIST = 2202;
        private static final String TAG = "PhotoRemaster.Result";
        private final Bitmap mRemasteredBitmap;
        private final JSONObject mResultJson;

        @Retention(RetentionPolicy.SOURCE)
        public @interface getParameterIds {}

        private Result(Bitmap remasteredBitmap) {
            this.mResultJson = new JSONObject();
            this.mRemasteredBitmap = remasteredBitmap;
        }

        public String getParameter(int id) {
            try {
                return this.mResultJson.getString(String.valueOf(id));
            } catch (JSONException e) {
                if (e.getMessage() != null) {
                    LogUtil.e(TAG, e.getMessage());
                    return null;
                }
                LogUtil.e(TAG, "Failed to get string from mResultJson", e);
                return null;
            }
        }

        public void setParameter(int id, String val) {
            try {
                this.mResultJson.put(String.valueOf(id), val);
            } catch (JSONException e) {
                if (e.getMessage() != null) {
                    LogUtil.e(TAG, e.getMessage());
                } else {
                    LogUtil.e(TAG, "Failed to get string from mResultJson", e);
                }
                throw new RuntimeException(e);
            }
        }

        public Bitmap getRemasteredBitmap() {
            return this.mRemasteredBitmap;
        }
    }

    private Result getParameters() {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        final Result result =
                new Result(
                        this.mBuilder.mBitmap == null
                                ? null
                                : this.mServiceClient.getBitmapParam(ResultParam.OUTPUT_BITMAP.ID));
        String remasteredPathName = this.mServiceClient.getStringParam(1003);
        if (this.mBuilder.mRequestFocusRoi && remasteredPathName != null) {
            result.setParameter(
                    ResultParam.FOCUS_ROI.ID,
                    this.mServiceClient.getFocusRoi(
                            this.mBuilder.mInputPathName, remasteredPathName));
        }
        Arrays.stream(ResultParam.values())
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return SemPhotoRemaster.lambda$getParameters$0(
                                        (SemPhotoRemaster.ResultParam) obj);
                            }
                        })
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return SemPhotoRemaster.lambda$getParameters$1(
                                        (SemPhotoRemaster.ResultParam) obj);
                            }
                        })
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda2
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SemPhotoRemaster.this.lambda$getParameters$2(
                                        result, (SemPhotoRemaster.ResultParam) obj);
                            }
                        });
        Arrays.stream(ResultParam.values())
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda3
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return SemPhotoRemaster.lambda$getParameters$3(
                                        (SemPhotoRemaster.ResultParam) obj);
                            }
                        })
                .filter(
                        new Predicate() { // from class:
                                          // com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda4
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return SemPhotoRemaster.lambda$getParameters$4(
                                        (SemPhotoRemaster.ResultParam) obj);
                            }
                        })
                .forEach(
                        new Consumer() { // from class:
                                         // com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda5
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                SemPhotoRemaster.this.lambda$getParameters$5(
                                        result, (SemPhotoRemaster.ResultParam) obj);
                            }
                        });
        return result;
    }

    static /* synthetic */ boolean lambda$getParameters$0(ResultParam param) {
        return param.DATA_TYPE == ResultParam.ParamDataType.STRING;
    }

    static /* synthetic */ boolean lambda$getParameters$1(ResultParam param) {
        return !param.ONDEMAND;
    }

    public /* synthetic */ void lambda$getParameters$2(Result result, ResultParam param) {
        String paramData = this.mServiceClient.getStringParam(param.ID);
        LogUtil.d(TAG, "getStringParam(" + param.ID + ") : " + paramData);
        result.setParameter(param.ID, paramData);
    }

    static /* synthetic */ boolean lambda$getParameters$3(ResultParam param) {
        return param.DATA_TYPE == ResultParam.ParamDataType.LONG;
    }

    static /* synthetic */ boolean lambda$getParameters$4(ResultParam param) {
        return !param.ONDEMAND;
    }

    public /* synthetic */ void lambda$getParameters$5(Result result, ResultParam param) {
        String paramData = String.valueOf(this.mServiceClient.getLongParam(param.ID));
        LogUtil.d(TAG, "getLongParam(" + param.ID + ") : " + paramData);
        result.setParameter(param.ID, paramData);
    }

    private Result doRemaster(int processMode, List<Integer> enhanceModes, Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        synchronized (SemPhotoRemaster.class) {
            synchronized (mStopLock) {
                if (this.mServiceClient != null) {
                    LogUtil.e(TAG, "Error mServiceClient is not null at begin of doRemaster");
                }
                this.mServiceClient = new ClientRemasterDirector();
                if (this.mBuilder.mTryInit) {
                    LogUtil.d(TAG, "TryInit is started.");
                    if (!this.mServiceClient.tryInit(context)) {
                        LogUtil.w(TAG, "Failed to tryInit()");
                        this.mServiceClient = null;
                        return null;
                    }
                } else {
                    this.mServiceClient.init(context);
                }
                setParameters(context);
                boolean processingSuccess =
                        this.mServiceClient.processImage(processMode, enhanceModes);
                Result result = processingSuccess ? getParameters() : null;
                if (processingSuccess) {
                    LogUtil.d(TAG, "Raw Result: " + result.mResultJson);
                }
                synchronized (mStopLock) {
                    this.mServiceClient.deinit();
                    this.mServiceClient = null;
                }
                return result;
            }
        }
    }

    public boolean stop() {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        synchronized (mStopLock) {
            if (this.mServiceClient == null) {
                LogUtil.i(TAG, "Service is already stopped.");
                return true;
            }
            this.mServiceClient.stop();
            return true;
        }
    }

    public Result findEnhancementType(Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        List<Integer> enhanceModes = new ArrayList<>();
        enhanceModes.add(-1);
        return doRemaster(1, enhanceModes, context);
    }

    public Result remaster(Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        List<Integer> enhanceModes = new ArrayList<>();
        enhanceModes.add(0);
        return doRemaster(0, enhanceModes, context);
    }

    public Result remaster(List<Integer> enhanceTypes, Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        return doRemaster(2, enhanceTypes, context);
    }
}
