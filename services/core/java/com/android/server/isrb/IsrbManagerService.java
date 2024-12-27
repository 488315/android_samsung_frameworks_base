package com.android.server.isrb;

import android.content.Context;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.util.Log;

import com.android.server.SystemService;
import com.android.server.isrb.IsrbManagerServiceImpl.ServiceHandler;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IsrbManagerService extends SystemService {
    public final IsrbManagerServiceImpl mIsrbManagerServiceImpl;

    public IsrbManagerService(Context context) {
        super(context);
        IsrbManagerServiceImpl isrbManagerServiceImpl = new IsrbManagerServiceImpl();
        isrbManagerServiceImpl.mSystemReady = false;
        isrbManagerServiceImpl.mBootComplete = false;
        isrbManagerServiceImpl.mIsSystemErrPopup = false;
        isrbManagerServiceImpl.mIsNetworkReady = false;
        isrbManagerServiceImpl.mErrAlertNum = 0;
        isrbManagerServiceImpl.mPreviousTipsTime = 0;
        isrbManagerServiceImpl.mPreviousTipsDate = 0;
        isrbManagerServiceImpl.mPreviousTipsMonth = 0;
        isrbManagerServiceImpl.mPreviousTipsYear = 0;
        isrbManagerServiceImpl.mContext = context;
        this.mIsrbManagerServiceImpl = isrbManagerServiceImpl;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        IsrbManagerServiceImpl isrbManagerServiceImpl = this.mIsrbManagerServiceImpl;
        if (i == 600) {
            if (!isrbManagerServiceImpl.mSystemReady) {
                isrbManagerServiceImpl.mSystemReady = true;
            }
            HandlerThread handlerThread = new HandlerThread("MessageISRBThread", 10);
            handlerThread.start();
            isrbManagerServiceImpl.mLooper = handlerThread.getLooper();
            isrbManagerServiceImpl.mHandler =
                    isrbManagerServiceImpl.new ServiceHandler(isrbManagerServiceImpl.mLooper);
            return;
        }
        if (i == 1000) {
            if (!isrbManagerServiceImpl.mBootComplete) {
                isrbManagerServiceImpl.mBootComplete = true;
            }
            if (SystemProperties.getBoolean("persist.sys.enable_isrb", false)) {
                isrbManagerServiceImpl.mHandler.sendEmptyMessageDelayed(3, 500L);
                Log.d("IsrbManagerServiceImpl", "PROP_ENABLE_ISRB:disable");
                IsrbManagerServiceImpl.ServiceHandler serviceHandler =
                        isrbManagerServiceImpl.mHandler;
                serviceHandler.sendMessageDelayed(serviceHandler.obtainMessage(1), 45000L);
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("isrb", this.mIsrbManagerServiceImpl);
    }
}
