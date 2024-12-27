package com.samsung.android.sume.core.message;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda2;
import com.samsung.android.sume.core.message.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public final class Request extends Message {
    private static final String TAG = Def.tagOf((Class<?>) Request.class);
    private List<MediaBuffer> inputBufferList;
    private boolean isOneWay;
    private List<MediaBuffer> outputBufferList;
    private Messenger receiver;

    private Request(android.os.Message message) {
        super(message);
        this.isOneWay = false;
        Bundle bundle = message.getData();
        Parcelable[] inputBuffers = bundle.getParcelableArray("input-buffer-list");
        if (inputBuffers != null) {
            this.inputBufferList = (List) Arrays.stream(inputBuffers).map(new Function() { // from class: com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Request.lambda$new$0((Parcelable) obj);
                }
            }).collect(Collectors.toList());
        }
        Parcelable[] outputBuffers = bundle.getParcelableArray("output-buffer-list");
        if (outputBuffers != null) {
            this.outputBufferList = (List) Arrays.stream(outputBuffers).map(new Function() { // from class: com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Request.lambda$new$1((Parcelable) obj);
                }
            }).collect(Collectors.toList());
        }
    }

    static /* synthetic */ MediaBuffer lambda$new$0(Parcelable it) {
        return (MediaBuffer) it;
    }

    static /* synthetic */ MediaBuffer lambda$new$1(Parcelable it) {
        return (MediaBuffer) it;
    }

    private Request(int code) {
        super(991, code);
        this.isOneWay = false;
    }

    @Override // com.samsung.android.sume.core.message.Message
    public android.os.Message toAndroidMessage() {
        android.os.Message msg = super.toAndroidMessage();
        Bundle bundle = msg.getData();
        if (this.inputBufferList != null) {
            bundle.putParcelableArray("input-buffer-list", (Parcelable[]) this.inputBufferList.toArray(new MediaBuffer[0]));
        }
        if (this.outputBufferList != null) {
            bundle.putParcelableArray("output-buffer-list", (Parcelable[]) this.outputBufferList.toArray(new MediaBuffer[0]));
        }
        return msg;
    }

    public Request setInputBuffer(MediaBuffer... mediaBuffers) {
        this.inputBufferList = Arrays.asList(mediaBuffers);
        return this;
    }

    public Request setInputBuffer(List<MediaBuffer> mediaBufferList) {
        this.inputBufferList = mediaBufferList;
        return this;
    }

    public Request setOutputBuffer(MediaBuffer... mediaBuffers) {
        this.outputBufferList = Arrays.asList(mediaBuffers);
        return this;
    }

    public Request setOutputBuffer(List<MediaBuffer> mediaBufferList) {
        this.outputBufferList = mediaBufferList;
        return this;
    }

    public Messenger getReceiver() {
        return this.receiver;
    }

    public Request setReceiver(Messenger receiver) {
        this.receiver = receiver;
        return this;
    }

    public Request asOneWay() {
        this.isOneWay = true;
        return this;
    }

    public boolean isOneWay() {
        return this.isOneWay;
    }

    @Override // com.samsung.android.sume.core.message.Message
    public Message post() {
        List<MediaBuffer> list;
        MediaBufferGroup$$ExternalSyntheticLambda2 mediaBufferGroup$$ExternalSyntheticLambda2;
        if (this.receiver == null) {
            Log.d(TAG, "no receiver object given for code " + getCode() + ", skip to send");
            return this;
        }
        try {
            try {
                Log.d(TAG, "send request: " + this);
                this.receiver.send(toAndroidMessage());
                if (this.inputBufferList != null) {
                    this.inputBufferList.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
                }
            } catch (RemoteException e) {
                Log.w(TAG, "fail to send request: " + e.getMessage());
                if (this.inputBufferList != null) {
                    this.inputBufferList.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
                }
                if (this.outputBufferList != null) {
                    list = this.outputBufferList;
                    mediaBufferGroup$$ExternalSyntheticLambda2 = new MediaBufferGroup$$ExternalSyntheticLambda2();
                }
            } catch (Exception e2) {
                Log.w(TAG, "Exception: " + e2.getMessage());
                e2.printStackTrace();
                if (this.inputBufferList != null) {
                    this.inputBufferList.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
                }
                if (this.outputBufferList != null) {
                    list = this.outputBufferList;
                    mediaBufferGroup$$ExternalSyntheticLambda2 = new MediaBufferGroup$$ExternalSyntheticLambda2();
                }
            }
            if (this.outputBufferList != null) {
                list = this.outputBufferList;
                mediaBufferGroup$$ExternalSyntheticLambda2 = new MediaBufferGroup$$ExternalSyntheticLambda2();
                list.forEach(mediaBufferGroup$$ExternalSyntheticLambda2);
            }
            this.inputBufferList = null;
            this.outputBufferList = null;
            return this;
        } catch (Throwable th) {
            if (this.inputBufferList != null) {
                this.inputBufferList.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
            }
            if (this.outputBufferList != null) {
                this.outputBufferList.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
            }
            this.inputBufferList = null;
            this.outputBufferList = null;
            throw th;
        }
    }

    @Override // com.samsung.android.sume.core.message.Message
    public Message then(Consumer<Message> responseListener) {
        this.extra = Response.ListenerManager.getInstance().register(responseListener);
        return this;
    }

    public MediaBuffer getInputBuffer() {
        return (MediaBuffer) Optional.ofNullable(this.inputBufferList).map(new Function() { // from class: com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Request.lambda$getInputBuffer$2((List) obj);
            }
        }).orElse(null);
    }

    static /* synthetic */ MediaBuffer lambda$getInputBuffer$2(List it) {
        if (it.isEmpty()) {
            return null;
        }
        if (it.size() == 1) {
            ((MediaBuffer) it.get(0)).setExtra("singular-buffer", true);
            return (MediaBuffer) it.get(0);
        }
        return MediaBuffer.groupOf((List<MediaBuffer>) it);
    }

    public MediaBuffer getOutputBuffer() {
        return (MediaBuffer) Optional.ofNullable(this.outputBufferList).map(new Function() { // from class: com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Request.lambda$getOutputBuffer$3((List) obj);
            }
        }).orElse(null);
    }

    static /* synthetic */ MediaBuffer lambda$getOutputBuffer$3(List it) {
        if (it.isEmpty()) {
            return null;
        }
        return it.size() == 1 ? (MediaBuffer) it.get(0) : MediaBuffer.groupOf((List<MediaBuffer>) it);
    }

    public ContentValues getContentValues() {
        return (ContentValues) Optional.ofNullable(getContentValuesList()).flatMap(new Function() { // from class: com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Optional findFirst;
                findFirst = ((List) obj).stream().findFirst();
                return findFirst;
            }
        }).orElse(null);
    }

    @Override // com.samsung.android.sume.core.message.Message
    public String toString() {
        return contentToString();
    }

    public List<ContentValues> getContentValuesList() {
        return (List) get("content-values");
    }

    public static Request of(int code) {
        return new Request(code);
    }

    public static Request of(int code, String key, Object data) {
        return (Request) new Request(code).put(key, data);
    }

    public static Request of(android.os.Message msg) {
        return new Request(msg);
    }
}
