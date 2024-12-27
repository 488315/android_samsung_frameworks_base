package com.samsung.android.sdk.scs.ai.text;

import android.content.ContentResolver;
import android.content.Context;

import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.sdk.scs.base.utils.Log;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class TextServiceExecutor extends ThreadPoolExecutor {
    public final ContentResolver mTextContentResolver;
    public final AtomicInteger taskCount;

    public TextServiceExecutor(Context context) {
        super(1, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingDeque());
        this.taskCount = new AtomicInteger(0);
        allowCoreThreadTimeOut(true);
        Log.d("ScsApi@ProviderExecutor", "ProviderExecutor constructor()");
        this.mTextContentResolver = context.getContentResolver();
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public final void afterExecute(Runnable runnable, Throwable th) {
        super.afterExecute(runnable, th);
        this.taskCount.getAndDecrement();
        Log.d("ScsApi@ProviderExecutor", "afterExecute(). mTaskCount: " + this.taskCount);
    }

    @Override // java.util.concurrent.ThreadPoolExecutor
    public final void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        Object[] objArr = {this, runnable};
        StringBuilder sb = new StringBuilder("task");
        for (int i = 0; i < 2; i++) {
            Object obj = objArr[i];
            if (obj != null) {
                String simpleName = obj.getClass().getSimpleName();
                String hexString = Integer.toHexString(obj.hashCode());
                if (sb.length() > 0) {
                    sb.append(" >> ");
                }
                RCPManagerService$$ExternalSyntheticOutline0.m$1(sb, simpleName, "@", hexString);
            }
        }
        android.util.Log.i(Log.concatPrefixTag("ScsApi@ProviderExecutor"), sb.toString());
        Log.e("ScsApi@ProviderExecutor", "Unexpected runnable!!!!");
        this.taskCount.getAndIncrement();
        Log.d("ScsApi@ProviderExecutor", "beforeExecute(). mTaskCount: " + this.taskCount);
    }
}
