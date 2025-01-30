package com.samsung.android.server.corescpm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.server.corescpm.ScpmController;
import com.samsung.android.server.util.CoreLogger;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class ScpmControllerImpl extends BroadcastReceiver implements ScpmController {
    public static final long LAZY_BOOT_COMPLETED_TIMEOUT_DELAY;
    public static final long RETRY_TIMES;
    public static final Map sControllers = new ConcurrentHashMap();
    public Consumer mCallback;
    public final ScpmController.ConsumerInfo mConsumerInfo;
    public Context mContext;
    public Handler mHandler;
    public CoreLogger mLogger;
    public boolean mStarted;
    public String mToken;
    public final Runnable mOnLazyBootCompletedTimeout = new Runnable() { // from class: com.samsung.android.server.corescpm.ScpmControllerImpl$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            ScpmControllerImpl.this.onLazyBootCompletedTimeout();
        }
    };
    public final Runnable mTryRegister = new Runnable() { // from class: com.samsung.android.server.corescpm.ScpmControllerImpl$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            ScpmControllerImpl.this.tryRegister();
        }
    };
    public final Object mLock = new Object();
    public int mRetryNumber = 3;

    static {
        boolean z = CoreRune.SAFE_DEBUG;
        LAZY_BOOT_COMPLETED_TIMEOUT_DELAY = (z ? 20 : 60) * 60000;
        RETRY_TIMES = (z ? 10 : 20) * 60000;
    }

    public static synchronized ScpmController getScpmController(ScpmController.ConsumerInfo consumerInfo) {
        ScpmController scpmController;
        synchronized (ScpmControllerImpl.class) {
            Map map = sControllers;
            scpmController = (ScpmController) map.get(consumerInfo);
            if (scpmController == null) {
                scpmController = new ScpmControllerImpl(consumerInfo);
                map.put(consumerInfo, scpmController);
            }
        }
        return scpmController;
    }

    public ScpmControllerImpl(ScpmController.ConsumerInfo consumerInfo) {
        Objects.requireNonNull(consumerInfo.mPackageName, "PackageName");
        Objects.requireNonNull(consumerInfo.mReceiverPackageName, "ReceiverPackageName");
        Objects.requireNonNull(consumerInfo.mAppId, "AppId");
        Objects.requireNonNull(consumerInfo.mVersion, "Version");
        this.mConsumerInfo = consumerInfo;
    }

    @Override // com.samsung.android.server.corescpm.ScpmController
    public void registerScpm(Context context, Handler handler, final Set set, Consumer consumer, CoreLogger coreLogger) {
        Objects.requireNonNull(context, "Context");
        Objects.requireNonNull(handler, "Handler");
        Objects.requireNonNull(set, "Policies");
        Objects.requireNonNull(consumer, "Callback");
        Objects.requireNonNull(coreLogger, "Logger");
        synchronized (this.mLock) {
            if (this.mStarted) {
                return;
            }
            this.mContext = context;
            this.mHandler = handler;
            this.mCallback = consumer;
            this.mLogger = coreLogger;
            this.mStarted = true;
            handler.post(new Runnable() { // from class: com.samsung.android.server.corescpm.ScpmControllerImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ScpmControllerImpl.this.lambda$registerScpm$0(set);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$registerScpm$0(Set set) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.intent.action.LAZY_BOOT_COMPLETE");
        intentFilter.addAction(KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            intentFilter.addAction("com.samsung.android.scpm.policy.UPDATE." + ((String) it.next()));
        }
        this.mContext.registerReceiver(this, intentFilter);
        this.mHandler.postDelayed(this.mOnLazyBootCompletedTimeout, LAZY_BOOT_COMPLETED_TIMEOUT_DELAY);
    }

    @Override // com.samsung.android.server.corescpm.ScpmController
    public FileDescriptor getFileDescriptor(String str) {
        synchronized (this.mLock) {
            if (!this.mStarted) {
                return null;
            }
            try {
                return getFileDescriptorInternal(str);
            } catch (Throwable th) {
                this.mLogger.log(6, "Failed to getFileDescriptor from SCPM.", th);
                return null;
            }
        }
    }

    public final FileDescriptor getFileDescriptorInternal(String str) {
        if (!register()) {
            this.mLogger.log(6, "getFileDescriptor: token is null");
            return null;
        }
        Uri parse = Uri.parse("content://com.samsung.android.scpm.policy/" + this.mToken + "/" + str);
        ParcelFileDescriptor openFileDescriptor = this.mContext.getContentResolver().openFileDescriptor(parse, "r");
        if (openFileDescriptor == null) {
            Bundle bundle = new Bundle();
            bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, this.mToken);
            Bundle callScpmApi = callScpmApi(parse, "getLastError", bundle);
            if (callScpmApi == null) {
                this.mLogger.log(6, "getFileDescriptor: bundle is null");
                return null;
            }
            this.mLogger.log(6, "getFileDescriptor: code=" + callScpmApi.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1) + ", msg=" + callScpmApi.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE));
            return null;
        }
        return openFileDescriptor.getFileDescriptor();
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent == null) {
            this.mLogger.log(6, "onReceive, intent is null");
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            this.mLogger.log(5, "onReceive, action is empty");
            return;
        }
        int lastIndexOf = action.lastIndexOf(".");
        int i = lastIndexOf + 1;
        int length = action.length();
        if (lastIndexOf < 0 || i == length) {
            this.mLogger.log(5, "onReceive, action=" + action);
        } else {
            this.mLogger.log(3, "onReceive, action=" + action.substring(i, length));
        }
        if ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(action)) {
            this.mHandler.removeCallbacks(this.mOnLazyBootCompletedTimeout);
            onLazyBootCompleted();
        } else {
            if (KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
                synchronized (this.mLock) {
                    this.mToken = null;
                }
                this.mRetryNumber = 3;
                this.mHandler.removeCallbacks(this.mTryRegister);
                this.mHandler.postDelayed(this.mTryRegister, 180000L);
                return;
            }
            this.mCallback.accept(action.replace("com.samsung.android.scpm.policy.UPDATE.", ""));
        }
    }

    public final void onLazyBootCompletedTimeout() {
        this.mLogger.log(5, "onLazyBootCompletedTimeout");
        onLazyBootCompleted();
    }

    public final void onLazyBootCompleted() {
        synchronized (this.mLock) {
            if (this.mToken != null) {
                return;
            }
            this.mHandler.postDelayed(this.mTryRegister, 0L);
        }
    }

    public final void tryRegister() {
        try {
            if (register()) {
                this.mCallback.accept(null);
                return;
            }
        } catch (Throwable th) {
            this.mLogger.log(5, "Failed to tryRegister", th);
        }
        int i = this.mRetryNumber;
        this.mRetryNumber = i - 1;
        if (i > 0) {
            this.mHandler.removeCallbacks(this.mTryRegister);
            this.mHandler.postDelayed(this.mTryRegister, RETRY_TIMES);
        } else {
            this.mLogger.log(6, "SCPM registration fails");
        }
    }

    public final boolean register() {
        synchronized (this.mLock) {
            boolean z = true;
            if (this.mToken != null) {
                return true;
            }
            Bundle bundle = new Bundle();
            bundle.putString("packageName", this.mConsumerInfo.mPackageName);
            bundle.putString("appId", this.mConsumerInfo.mAppId);
            bundle.putString("version", this.mConsumerInfo.mVersion);
            bundle.putString("receiverPackageName", this.mConsumerInfo.mReceiverPackageName);
            Bundle callScpmApi = callScpmApi(ScpmApiContract.URI, "register", bundle);
            if (callScpmApi == null) {
                this.mLogger.log(6, "Fail to register, bundle is null");
                return false;
            }
            String string = callScpmApi.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN);
            this.mLogger.log(4, "Register, result=" + callScpmApi.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 2) + ", code=" + callScpmApi.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1) + ", msg=" + callScpmApi.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE));
            synchronized (this.mLock) {
                this.mToken = string;
                if (string == null) {
                    z = false;
                }
            }
            return z;
        }
    }

    public final Bundle callScpmApi(Uri uri, String str, Bundle bundle) {
        return this.mContext.getContentResolver().call(uri, str, this.mConsumerInfo.mPackageName, bundle);
    }

    @Override // com.samsung.android.server.corescpm.ScpmController
    public void dump(PrintWriter printWriter) {
        synchronized (this.mLock) {
            printWriter.println("SCPMv2 Token=" + this.mToken);
        }
    }
}
