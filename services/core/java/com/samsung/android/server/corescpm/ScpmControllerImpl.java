package com.samsung.android.server.corescpm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.server.packagefeature.core.PackageFeatureManagerService;
import com.samsung.android.server.util.CoreLogger;

import java.io.FileDescriptor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public final class ScpmControllerImpl extends BroadcastReceiver implements ScpmController {
    public static final Map sControllers = new ConcurrentHashMap();
    public Consumer mCallback;
    public final ScpmController.ConsumerInfo mConsumerInfo;
    public Context mContext;
    public Handler mHandler;
    public CoreLogger mLogger;
    public final ScpmControllerImpl$$ExternalSyntheticLambda0 mOnLazyBootCompletedTimeout;
    public boolean mStarted;
    public String mToken;
    public final ScpmControllerImpl$$ExternalSyntheticLambda0 mTryRegister;
    public final Object mLock = new Object();
    public int mRetryNumber = 3;

    public ScpmControllerImpl(PackageFeatureManagerService.ScpmConsumerInfo scpmConsumerInfo) {
        final int i = 0;
        this.mOnLazyBootCompletedTimeout =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.server.corescpm.ScpmControllerImpl$$ExternalSyntheticLambda0
                    public final /* synthetic */ ScpmControllerImpl f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i2 = i;
                        ScpmControllerImpl scpmControllerImpl = this.f$0;
                        switch (i2) {
                            case 0:
                                scpmControllerImpl.mLogger.log(
                                        5, "onLazyBootCompletedTimeout", null);
                                scpmControllerImpl.onLazyBootCompleted();
                                break;
                            default:
                                scpmControllerImpl.getClass();
                                try {
                                    if (scpmControllerImpl.register()) {
                                        scpmControllerImpl.mCallback.accept(null);
                                        break;
                                    }
                                } catch (Throwable th) {
                                    scpmControllerImpl.mLogger.log(5, "Failed to tryRegister", th);
                                }
                                int i3 = scpmControllerImpl.mRetryNumber;
                                scpmControllerImpl.mRetryNumber = i3 - 1;
                                if (i3 <= 0) {
                                    scpmControllerImpl.mLogger.log(
                                            6, "SCPM registration fails", null);
                                    break;
                                } else {
                                    scpmControllerImpl.mHandler.removeCallbacks(
                                            scpmControllerImpl.mTryRegister);
                                    scpmControllerImpl.mHandler.postDelayed(
                                            scpmControllerImpl.mTryRegister, 1200000L);
                                    break;
                                }
                        }
                    }
                };
        final int i2 = 1;
        this.mTryRegister =
                new Runnable(
                        this) { // from class:
                                // com.samsung.android.server.corescpm.ScpmControllerImpl$$ExternalSyntheticLambda0
                    public final /* synthetic */ ScpmControllerImpl f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i22 = i2;
                        ScpmControllerImpl scpmControllerImpl = this.f$0;
                        switch (i22) {
                            case 0:
                                scpmControllerImpl.mLogger.log(
                                        5, "onLazyBootCompletedTimeout", null);
                                scpmControllerImpl.onLazyBootCompleted();
                                break;
                            default:
                                scpmControllerImpl.getClass();
                                try {
                                    if (scpmControllerImpl.register()) {
                                        scpmControllerImpl.mCallback.accept(null);
                                        break;
                                    }
                                } catch (Throwable th) {
                                    scpmControllerImpl.mLogger.log(5, "Failed to tryRegister", th);
                                }
                                int i3 = scpmControllerImpl.mRetryNumber;
                                scpmControllerImpl.mRetryNumber = i3 - 1;
                                if (i3 <= 0) {
                                    scpmControllerImpl.mLogger.log(
                                            6, "SCPM registration fails", null);
                                    break;
                                } else {
                                    scpmControllerImpl.mHandler.removeCallbacks(
                                            scpmControllerImpl.mTryRegister);
                                    scpmControllerImpl.mHandler.postDelayed(
                                            scpmControllerImpl.mTryRegister, 1200000L);
                                    break;
                                }
                        }
                    }
                };
        this.mConsumerInfo = scpmConsumerInfo;
    }

    public final FileDescriptor getFileDescriptorInternal(String str) {
        if (!register()) {
            this.mLogger.log(6, "getFileDescriptor: token is null", null);
            return null;
        }
        Uri parse =
                Uri.parse("content://com.samsung.android.scpm.policy/" + this.mToken + "/" + str);
        ParcelFileDescriptor openFileDescriptor =
                this.mContext.getContentResolver().openFileDescriptor(parse, "r");
        if (openFileDescriptor != null) {
            return openFileDescriptor.getFileDescriptor();
        }
        Bundle bundle = new Bundle();
        bundle.putString(KnoxCustomManagerService.SPCM_KEY_TOKEN, this.mToken);
        Bundle call =
                this.mContext
                        .getContentResolver()
                        .call(parse, "getLastError", this.mConsumerInfo.mPackageName, bundle);
        if (call == null) {
            this.mLogger.log(6, "getFileDescriptor: bundle is null", null);
            return null;
        }
        this.mLogger.log(
                6,
                "getFileDescriptor: code="
                        + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1)
                        + ", msg="
                        + call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE),
                null);
        return null;
    }

    public final void onLazyBootCompleted() {
        synchronized (this.mLock) {
            try {
                if (this.mToken != null) {
                    return;
                }
                this.mHandler.postDelayed(this.mTryRegister, 0L);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (intent == null) {
            this.mLogger.log(6, "onReceive, intent is null", null);
            return;
        }
        String action = intent.getAction();
        if (TextUtils.isEmpty(action)) {
            this.mLogger.log(5, "onReceive, action is empty", null);
            return;
        }
        int lastIndexOf = action.lastIndexOf(".");
        int i = lastIndexOf + 1;
        int length = action.length();
        if (lastIndexOf < 0 || i == length) {
            this.mLogger.log(5, "onReceive, action=".concat(action), null);
        } else {
            this.mLogger.log(3, "onReceive, action=" + action.substring(i, length), null);
        }
        if ("com.samsung.intent.action.LAZY_BOOT_COMPLETE".equals(action)) {
            this.mHandler.removeCallbacks(this.mOnLazyBootCompletedTimeout);
            onLazyBootCompleted();
        } else {
            if (!KnoxCustomManagerService.ACTION_SCPM_POLICY_CLEAR_DATA.equals(action)) {
                this.mCallback.accept(
                        action.replace("com.samsung.android.scpm.policy.UPDATE.", ""));
                return;
            }
            synchronized (this.mLock) {
                this.mToken = null;
            }
            this.mRetryNumber = 3;
            this.mHandler.removeCallbacks(this.mTryRegister);
            this.mHandler.postDelayed(this.mTryRegister, 180000L);
        }
    }

    public final boolean register() {
        synchronized (this.mLock) {
            try {
                boolean z = true;
                if (this.mToken != null) {
                    return true;
                }
                Bundle bundle = new Bundle();
                bundle.putString("packageName", this.mConsumerInfo.mPackageName);
                bundle.putString("appId", this.mConsumerInfo.mAppId);
                bundle.putString("version", this.mConsumerInfo.mVersion);
                bundle.putString("receiverPackageName", this.mConsumerInfo.mReceiverPackageName);
                Bundle call =
                        this.mContext
                                .getContentResolver()
                                .call(
                                        ScpmApiContract.URI,
                                        "register",
                                        this.mConsumerInfo.mPackageName,
                                        bundle);
                if (call == null) {
                    this.mLogger.log(6, "Fail to register, bundle is null", null);
                    return false;
                }
                String string = call.getString(KnoxCustomManagerService.SPCM_KEY_TOKEN);
                this.mLogger.log(
                        4,
                        "Register, result="
                                + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT, 2)
                                + ", code="
                                + call.getInt(KnoxCustomManagerService.SPCM_KEY_RESULT_CODE, -1)
                                + ", msg="
                                + call.getString(KnoxCustomManagerService.SPCM_KEY_RESULT_MESSAGE),
                        null);
                synchronized (this.mLock) {
                    this.mToken = string;
                    if (string == null) {
                        z = false;
                    }
                }
                return z;
            } finally {
            }
        }
    }
}
