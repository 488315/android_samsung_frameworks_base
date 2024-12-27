package com.samsung.android.infoextraction;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class SemInfoExtractionManager {
    private static final String EXTRACTED_INFO_DATA = "SemExtractedInfo";
    private static final String EXTRACTION_DATA_TYPE = "data_type";
    private static final String EXTRACTION_REQ_DATA = "req_data";
    private static final String EXTRACTION_REQ_TIME = "req_time";
    private static final int MSG_EXTRACTION_CALCEL = 7073;
    private static final int MSG_EXTRACTION_END = 7072;
    private static final int MSG_EXTRACTION_START = 7071;
    private static final int STRING_DATA_TYPE = 1;
    private static final int STROKE_DATA_TYPE = 3;
    private static String TAG = "semInfoextration";
    private static final int URI_DATA_TYPE = 2;
    private Context mContext;
    private IBinder mInfoExtractionService;
    private long mRequestNumber = -1;
    private ServiceConnection mConnection = null;
    public InfoExtractionListener mInfoExtractionListener = null;
    public OnExtractionCompletedListener mOnExtractionCompletedListener = null;

    public enum ExtractedInfoType {
        UNKNOWN,
        DATE_TIME,
        EMAIL,
        EVENT,
        HOTKEYWORD,
        ORIGINAL,
        PLACE,
        TELNUM,
        URL
    }

    public interface InfoExtractionListener {
        void onCompleted(int i, List<SemExtractedInfo> list);
    }

    public interface OnExtractionCompletedListener {
        void onExtractionCompleted(long j, List<SemExtractedInfo> list);
    }

    private static class UIBundleKey {
        private static final String CONTENTS = "contents";
        private static final String DISMISS = "dismiss";
        private static final String POSITION = "position";

        private UIBundleKey() {
        }
    }

    public SemInfoExtractionManager(Context context) throws IllegalStateException {
        this.mContext = null;
        Log.d(TAG, "SemInfoExtractionManager setting...");
        if (context == null) {
            Log.d(TAG, "Could not get the SemInfoExtraction service. -> context is NULL");
            throw new IllegalStateException("Could not get the SemInfoExtraction service. -> context is NULL");
        }
        this.mContext = context;
        if (isPenFeatureModel(this.mContext)) {
            Log.d(TAG, "SemInfoExtractionManager call by : " + this.mContext.getPackageName());
        } else {
            Log.d(TAG, "SemInfoExtraction only use for Pen Feature models.");
            throw new IllegalStateException("SemInfoExtraction only use for Pen Feature models.");
        }
    }

    private boolean isPenFeatureModel(Context context) {
        int uspLevel = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION", 0);
        if (uspLevel <= 0) {
            Log.d(TAG, "isPenFeatureModel : Pen is not supported, uspLevel=" + uspLevel);
            return false;
        }
        return true;
    }

    public void setInfoExtractionListener(InfoExtractionListener infoExtractionListener) throws IllegalArgumentException {
        if (infoExtractionListener == null) {
            Log.d(TAG, "infoExtractionListener is null");
            throw new IllegalArgumentException("infoExtractionListener is null");
        }
        this.mInfoExtractionListener = infoExtractionListener;
    }

    public void setOnExtractionCompletedListener(OnExtractionCompletedListener onExtractionCompletedListener) throws IllegalArgumentException {
        if (onExtractionCompletedListener == null) {
            Log.d(TAG, "onExtractionCompletedListener is null");
            throw new IllegalArgumentException("onExtractionCompletedListener is null");
        }
        this.mOnExtractionCompletedListener = onExtractionCompletedListener;
    }

    public long extract(String requestString) throws IllegalArgumentException, IllegalStateException {
        if (requestString == null) {
            return -1L;
        }
        setRequestNumber();
        startExtraction(1, requestString);
        return this.mRequestNumber;
    }

    public long extract(Uri requestUri) throws IllegalArgumentException, IllegalStateException {
        if (requestUri == null) {
            return -1L;
        }
        setRequestNumber();
        startExtraction(2, requestUri);
        return this.mRequestNumber;
    }

    public long extract(SemStrokeData requestSemStrokeData) throws IllegalArgumentException, IllegalStateException {
        if (requestSemStrokeData == null) {
            return -1L;
        }
        setRequestNumber();
        startExtraction(3, requestSemStrokeData);
        return this.mRequestNumber;
    }

    public long extract(ArrayList<SemStrokeData> requestSemStrokeDataList) throws IllegalArgumentException, IllegalStateException {
        if (requestSemStrokeDataList == null) {
            return -1L;
        }
        setRequestNumber();
        startExtraction(3, requestSemStrokeDataList);
        return this.mRequestNumber;
    }

    private void setRequestNumber() {
        this.mRequestNumber = System.currentTimeMillis();
    }

    public void showLinkPreview(String urlStr, Rect rect) throws IllegalArgumentException {
        if (urlStr == null) {
            throw new IllegalStateException("urlStr is null");
        }
        Log.d(TAG, "infoExtractionListener is null");
        try {
            Log.d(TAG, "showLinkPreview");
            Intent intent = new Intent();
            intent.setPackage("com.samsung.android.service.airviewdictionary");
            intent.setAction("com.samsung.android.service.hermes.HermesTickerService");
            intent.putExtra("contents", urlStr);
            intent.putExtra("position", rect);
            intent.putExtra("dismiss", false);
            this.mContext.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideLinkPreview() throws IllegalStateException {
        try {
            Log.d(TAG, "hideLinkPreview");
            Intent intent = new Intent();
            intent.setPackage("com.samsung.android.service.airviewdictionary");
            intent.setAction("com.samsung.android.service.hermes.HermesTickerService");
            intent.putExtra("dismiss", true);
            this.mContext.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void training(String source) throws IllegalStateException, IllegalArgumentException {
        Log.d(TAG, "training doesn't support in this version");
    }

    public void addResultRule(int type, String source) throws IllegalStateException, IllegalArgumentException {
        Log.d(TAG, "addResultRule doesn't support in this version");
    }

    private boolean bindInfoExtractionService() {
        if (this.mContext == null) {
            Log.d(TAG, "mContext is NULL -> can't try to bind with InfoExtractionService! ");
            return false;
        }
        Intent intent = new Intent().setAction("com.samsung.android.service.hermes.InfoExtractionService");
        intent.setPackage("com.samsung.android.service.airviewdictionary");
        boolean ret = this.mContext.bindService(intent, this.mConnection, 1);
        if (!ret) {
            Log.d(TAG, "Failed to bind with InfoExtractionService service!");
        }
        return ret;
    }

    private void startExtraction(final int dataType, final Object reqObject) {
        if (this.mConnection == null) {
            Log.d(TAG, "mConnection is NULL");
            this.mConnection = new ServiceConnection() { // from class: com.samsung.android.infoextraction.SemInfoExtractionManager.1
                @Override // android.content.ServiceConnection
                public void onServiceConnected(ComponentName name, IBinder service) {
                    SemInfoExtractionManager.this.mInfoExtractionService = service;
                    SemInfoExtractionManager.this.requestInfoExtraction(service, dataType, reqObject);
                }

                @Override // android.content.ServiceConnection
                public void onServiceDisconnected(ComponentName name) {
                    SemInfoExtractionManager.this.mInfoExtractionService = null;
                }
            };
            Log.d(TAG, "start : Binding to InfoExtractionService...");
            bindInfoExtractionService();
            return;
        }
        Log.d(TAG, "mConnection is not NULL");
        if (this.mInfoExtractionService == null) {
            Log.d(TAG, "mInfoExtractionService == null");
            bindInfoExtractionService();
        } else {
            Log.d(TAG, "mInfoExtractionService != null");
            requestInfoExtraction(this.mInfoExtractionService, dataType, reqObject);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void requestInfoExtraction(IBinder InfoExtractionService, int dataType, Object reqObject) {
        Log.d(TAG, "requestInfoExtraction data type = " + dataType);
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRACTION_REQ_TIME, this.mRequestNumber);
        bundle.putInt(EXTRACTION_DATA_TYPE, dataType);
        switch (dataType) {
            case 1:
                bundle.putString(EXTRACTION_REQ_DATA, (String) reqObject);
                break;
            case 2:
                bundle.putString(EXTRACTION_REQ_DATA, reqObject.toString());
                break;
            case 3:
                bundle.putParcelableArrayList(EXTRACTION_REQ_DATA, (ArrayList) reqObject);
                break;
            default:
                Log.d(TAG, "can't make data type = " + dataType);
                break;
        }
        Message message = Message.obtain((Handler) null, MSG_EXTRACTION_START);
        message.setData(bundle);
        message.replyTo = new Messenger(new IncomingHandler());
        try {
            if (InfoExtractionService != null) {
                Messenger requestMessenger = new Messenger(InfoExtractionService);
                requestMessenger.send(message);
                Log.d(TAG, "request Extraction : success");
            } else {
                Log.d(TAG, "request Extraction : InfoExtractionService is null!");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    class IncomingHandler extends Handler {
        IncomingHandler() {
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Log.d(SemInfoExtractionManager.TAG, "received Extraction data : success");
            long receivedReqTime = msg.getData().getLong(SemInfoExtractionManager.EXTRACTION_REQ_TIME);
            new ArrayList();
            ArrayList<SemExtractedInfo> semExtractedInfoList = msg.getData().getParcelableArrayList(SemInfoExtractionManager.EXTRACTED_INFO_DATA);
            if (SemInfoExtractionManager.this.mOnExtractionCompletedListener != null) {
                Log.d(SemInfoExtractionManager.TAG, "sent to mOnExtractionCompletedListener ReqTime : " + receivedReqTime + " extracted size : " + semExtractedInfoList.size());
                SemInfoExtractionManager.this.mOnExtractionCompletedListener.onExtractionCompleted(receivedReqTime, semExtractedInfoList);
                SemInfoExtractionManager.this.mRequestNumber = -1L;
            } else {
                Log.d(SemInfoExtractionManager.TAG, "mInfoExtractionResultListener is NULL");
                if (SemInfoExtractionManager.this.mInfoExtractionListener != null) {
                    Log.d(SemInfoExtractionManager.TAG, "sent to InfoExtractionListener ReqTime : " + receivedReqTime + " extracted size : " + semExtractedInfoList.size());
                    SemInfoExtractionManager.this.mInfoExtractionListener.onCompleted((int) receivedReqTime, semExtractedInfoList);
                } else {
                    Log.d(SemInfoExtractionManager.TAG, "mInfoExtractionListener is NULL");
                }
                SemInfoExtractionManager.this.mRequestNumber = -1L;
            }
        }
    }
}
