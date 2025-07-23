package com.samsung.android.media.photoremaster;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;
import com.samsung.android.media.photoremaster.SemPhotoRemaster;
import com.samsung.android.photoremaster.IDirector;
import com.samsung.android.photoremaster.util.LogUtil;
import com.samsung.android.photoremasterservice.ClientRemasterDirector;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes6.dex */
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
        public @interface setLongParamaterIds {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface setStringParameterIds {
        }

        private Builder(Uri uri) {
            LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
            StringBuilder sb = new StringBuilder("Input file: ");
            sb.append(uri);
            LogUtil.d(TAG, sb.toString());
            this.mInputUri = uri;
            setInputPathName(uri.getPath());
            this.mBitmap = null;
        }

        private Builder(Bitmap bitmap) {
            LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
            LogUtil.d(TAG, "Input is bitmap");
            this.mInputUri = null;
            this.mInputPathName = null;
            this.mBitmap = bitmap;
        }

        public static Builder with(Uri uri) {
            return new Builder(uri);
        }

        public static Builder with(Bitmap bitmap) {
            return new Builder(bitmap);
        }

        public Builder setParameter(int i, String str) {
            this.mStringParams.add(new Pair<>(Integer.valueOf(i), str));
            return this;
        }

        public Builder setParameter(int i, long j) {
            this.mLongParams.add(new Pair<>(Integer.valueOf(i), Long.valueOf(j)));
            return this;
        }

        public Builder setRequestFocusRoi() {
            if (this.mInputUri == null) {
                throw new IllegalArgumentException("FocusView does not support Bitmap-Object-Input");
            }
            this.mRequestFocusRoi = true;
            return this;
        }

        public Builder setProgressUpdateListener(ProgressUpdateListener progressUpdateListener) {
            this.mListener = progressUpdateListener;
            return this;
        }

        public Builder setTryInit() {
            this.mTryInit = true;
            return this;
        }

        public SemPhotoRemaster build() {
            return new SemPhotoRemaster(this);
        }

        private static boolean isValidFilePath(String str) {
            File file = new File(str);
            return file.exists() && file.isFile();
        }

        private void setInputPathName(String str) {
            if (str == null || !isValidFilePath(str)) {
                throw new IllegalArgumentException("File does not exist or is inaccessible: " + str);
            }
            this.mInputPathName = str;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void updateInputPath(Context context) {
            if (this.mInputUri == null) {
                return;
            }
            Cursor query = context.getContentResolver().query(this.mInputUri, new String[]{"_data"}, null, null, null);
            try {
                if (query == null) {
                    LogUtil.d(TAG, "InputPath is set as inputUri.getPath()");
                } else {
                    int columnIndexOrThrow = query.getColumnIndexOrThrow("_data");
                    query.moveToFirst();
                    LogUtil.i(TAG, "InputPath is replaced with content provider");
                    this.mInputPathName = query.getString(columnIndexOrThrow);
                    LogUtil.d(TAG, "mInputPathName is updated as " + this.mInputPathName);
                }
                if (query != null) {
                    query.close();
                }
            } finally {
            }
        }
    }

    private SemPhotoRemaster(Builder builder) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        this.mBuilder = builder;
    }

    /* JADX WARN: Multi-variable type inference failed */
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
        for (Pair pair : this.mBuilder.mStringParams) {
            this.mServiceClient.setStringParam(((Integer) pair.first).intValue(), (String) pair.second);
        }
        for (Pair pair2 : this.mBuilder.mLongParams) {
            this.mServiceClient.setLongParam(((Integer) pair2.first).intValue(), ((Long) pair2.second).longValue());
        }
        if (this.mBuilder.mBitmap != null) {
            this.mServiceClient.setBitmapParam(1014, this.mBuilder.mBitmap);
        }
        if (this.mBuilder.mListener != null) {
            this.mServiceClient.setProgressUpdateListener(new IDirector.ProgressUpdateListener() { // from class: com.samsung.android.media.photoremaster.SemPhotoRemaster.1
                private final ProgressUpdateListener mListener;

                {
                    this.mListener = SemPhotoRemaster.this.mBuilder.mListener;
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

    /* JADX INFO: Access modifiers changed from: private */
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

        /* JADX INFO: Access modifiers changed from: private */
        enum ParamDataType {
            STRING,
            LONG,
            EXCEPTIONAL
        }

        ResultParam(int i, ParamDataType paramDataType, boolean z) {
            this.ID = i;
            this.DATA_TYPE = paramDataType;
            this.ONDEMAND = z;
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
        public @interface getParameterIds {
        }

        private Result(Bitmap bitmap) {
            this.mResultJson = new JSONObject();
            this.mRemasteredBitmap = bitmap;
        }

        public String getParameter(int i) {
            try {
                return this.mResultJson.getString(String.valueOf(i));
            } catch (JSONException e) {
                if (e.getMessage() != null) {
                    LogUtil.e(TAG, e.getMessage());
                    return null;
                }
                LogUtil.e(TAG, "Failed to get string from mResultJson", e);
                return null;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setParameter(int i, String str) {
            try {
                this.mResultJson.put(String.valueOf(i), str);
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
        final Result result = new Result(this.mBuilder.mBitmap == null ? null : this.mServiceClient.getBitmapParam(ResultParam.OUTPUT_BITMAP.ID));
        String stringParam = this.mServiceClient.getStringParam(1003);
        if (this.mBuilder.mRequestFocusRoi && stringParam != null) {
            result.setParameter(ResultParam.FOCUS_ROI.ID, this.mServiceClient.getFocusRoi(this.mBuilder.mInputPathName, stringParam));
        }
        Arrays.stream(ResultParam.values()).filter(new Predicate() { // from class: com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SemPhotoRemaster.lambda$getParameters$0((SemPhotoRemaster.ResultParam) obj);
            }
        }).filter(new Predicate() { // from class: com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SemPhotoRemaster.lambda$getParameters$1((SemPhotoRemaster.ResultParam) obj);
            }
        }).forEach(new Consumer() { // from class: com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SemPhotoRemaster.this.lambda$getParameters$2(result, (SemPhotoRemaster.ResultParam) obj);
            }
        });
        Arrays.stream(ResultParam.values()).filter(new Predicate() { // from class: com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SemPhotoRemaster.lambda$getParameters$3((SemPhotoRemaster.ResultParam) obj);
            }
        }).filter(new Predicate() { // from class: com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return SemPhotoRemaster.lambda$getParameters$4((SemPhotoRemaster.ResultParam) obj);
            }
        }).forEach(new Consumer() { // from class: com.samsung.android.media.photoremaster.SemPhotoRemaster$$ExternalSyntheticLambda5
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SemPhotoRemaster.this.lambda$getParameters$5(result, (SemPhotoRemaster.ResultParam) obj);
            }
        });
        return result;
    }

    static /* synthetic */ boolean lambda$getParameters$0(ResultParam resultParam) {
        return resultParam.DATA_TYPE == ResultParam.ParamDataType.STRING;
    }

    static /* synthetic */ boolean lambda$getParameters$1(ResultParam resultParam) {
        return !resultParam.ONDEMAND;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getParameters$2(Result result, ResultParam resultParam) {
        String stringParam = this.mServiceClient.getStringParam(resultParam.ID);
        LogUtil.d(TAG, "getStringParam(" + resultParam.ID + ") : " + stringParam);
        result.setParameter(resultParam.ID, stringParam);
    }

    static /* synthetic */ boolean lambda$getParameters$3(ResultParam resultParam) {
        return resultParam.DATA_TYPE == ResultParam.ParamDataType.LONG;
    }

    static /* synthetic */ boolean lambda$getParameters$4(ResultParam resultParam) {
        return !resultParam.ONDEMAND;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$getParameters$5(Result result, ResultParam resultParam) {
        String valueOf = String.valueOf(this.mServiceClient.getLongParam(resultParam.ID));
        LogUtil.d(TAG, "getLongParam(" + resultParam.ID + ") : " + valueOf);
        result.setParameter(resultParam.ID, valueOf);
    }

    private Result doRemaster(int i, List<Integer> list, Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        synchronized (SemPhotoRemaster.class) {
            Object obj = mStopLock;
            synchronized (obj) {
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
                boolean processImage = this.mServiceClient.processImage(i, list);
                Result parameters = processImage ? getParameters() : null;
                if (processImage) {
                    LogUtil.d(TAG, "Raw Result: " + parameters.mResultJson);
                }
                synchronized (obj) {
                    this.mServiceClient.deinit();
                    this.mServiceClient = null;
                }
                return parameters;
            }
        }
    }

    public boolean stop() {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        synchronized (mStopLock) {
            ClientRemasterDirector clientRemasterDirector = this.mServiceClient;
            if (clientRemasterDirector == null) {
                LogUtil.i(TAG, "Service is already stopped.");
                return true;
            }
            clientRemasterDirector.stop();
            return true;
        }
    }

    public Result findEnhancementType(Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        ArrayList arrayList = new ArrayList();
        arrayList.add(-1);
        return doRemaster(1, arrayList, context);
    }

    public Result remaster(Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        ArrayList arrayList = new ArrayList();
        arrayList.add(0);
        return doRemaster(0, arrayList, context);
    }

    public Result remaster(List<Integer> list, Context context) {
        LogUtil.i(TAG, new Throwable().getStackTrace()[0].getMethodName() + " is called");
        return doRemaster(2, list, context);
    }
}
