package com.samsung.android.sume.core.message;

import android.os.ConditionVariable;

import com.samsung.android.sume.core.functional.PlaceHolder;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/* loaded from: classes6.dex */
public class ResponseHolder implements PlaceHolder<Response> {
    private final ConditionVariable cv = new ConditionVariable();
    private final int requestCode;
    private Response response;
    private WeakReference<Request> weakRequest;

    public ResponseHolder(int code) {
        this.requestCode = code;
    }

    public ResponseHolder(Request request) {
        this.requestCode = request.getCode();
        this.weakRequest = new WeakReference<>(request);
    }

    public boolean contains() {
        return this.response != null;
    }

    public Response get() {
        return this.response;
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public void put(Response response) {
        this.response = response;
        if (this.weakRequest != null
                && this.weakRequest.get() != null
                && response.replyTo == null) {
            response.replyTo = this.weakRequest.get().replyTo;
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public Response reset() {
        Response ret = this.response;
        this.response = null;
        return ret;
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public boolean isEmpty() {
        return this.response == null;
    }

    @Override // com.samsung.android.sume.core.functional.PlaceHolder
    public boolean isNotEmpty() {
        return this.response != null;
    }

    public int getCode() {
        return this.requestCode;
    }

    public Response await() {
        this.cv.block();
        return this.response;
    }

    public Response await(int time, TimeUnit timeUnit) {
        if (this.cv.block(timeUnit.toMillis(time))) {
            return this.response;
        }
        return null;
    }

    public void signal() {
        this.cv.open();
    }
}
