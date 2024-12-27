package com.samsung.android.sume.core.message;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Response extends Message {
    private static final String TAG = Def.tagOf((Class<?>) Response.class);
    private List<MediaBuffer> bufferList;
    private Consumer<Message> responseListener;

    private Response(int code) {
        super(992, code);
    }

    private Response(android.os.Message msg) {
        super(msg);
        Bundle bundle = msg.getData();
        Parcelable[] buffers = bundle.getParcelableArray("buffer-list");
        if (buffers != null) {
            this.bufferList =
                    (List)
                            Arrays.stream(buffers)
                                    .map(
                                            new Function() { // from class:
                                                             // com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda2
                                                @Override // java.util.function.Function
                                                public final Object apply(Object obj) {
                                                    return Response.lambda$new$0((Parcelable) obj);
                                                }
                                            })
                                    .collect(Collectors.toList());
        }
        if (this.extra != 0) {
            this.responseListener = ListenerManager.getInstance().unRegister(this.extra);
        }
    }

    static /* synthetic */ MediaBuffer lambda$new$0(Parcelable it) {
        return (MediaBuffer) it;
    }

    private Response(Message message) {
        super(message);
        this.data = new HashMap();
        if (this.extra != 0) {
            this.responseListener = ListenerManager.getInstance().unRegister(this.extra);
        }
    }

    @Override // com.samsung.android.sume.core.message.Message
    public android.os.Message toAndroidMessage() {
        Log.d(TAG, "toAndroidMessage");
        android.os.Message msg = super.toAndroidMessage();
        if (this.bufferList != null) {
            Bundle bundle = msg.getData();
            bundle.putParcelableArray(
                    "buffer-list", (Parcelable[]) this.bufferList.toArray(new MediaBuffer[0]));
        }
        return msg;
    }

    public Response setBuffer(MediaBuffer... mediaBuffer) {
        this.bufferList = Arrays.asList(mediaBuffer);
        return this;
    }

    public Response setBuffer(List<MediaBuffer> mediaBufferList) {
        this.bufferList = mediaBufferList;
        return this;
    }

    public MediaBuffer getBuffer() {
        return (MediaBuffer)
                Optional.ofNullable(this.bufferList)
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda1
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return Response.this.m9195xed6285dd((List) obj);
                                    }
                                })
                        .orElse(null);
    }

    /* renamed from: lambda$getBuffer$1$com-samsung-android-sume-core-message-Response, reason: not valid java name */
    /* synthetic */ MediaBuffer m9195xed6285dd(List it) {
        if (it.isEmpty()) {
            return null;
        }
        return it.size() == 1
                ? this.bufferList.get(0)
                : MediaBuffer.groupOf((List<MediaBuffer>) it);
    }

    List<MediaBuffer> getBufferList() {
        return this.bufferList;
    }

    public Response join(Response other) {
        if (other.isOk()) {
            if (other.bufferList != null) {
                if (this.bufferList == null) {
                    this.bufferList = other.bufferList;
                } else {
                    this.bufferList.addAll(other.bufferList);
                }
            }
            this.data.putAll(other.data);
        } else {
            this.code = other.code;
            this.type = other.type;
            if (other.exception != null) {
                this.exception = other.exception;
            }
        }
        if (other.extra > 0) {
            this.extra = other.extra;
        }
        if (other.replyTo != null) {
            this.replyTo = other.replyTo;
        }
        if (other.responseListener != null) {
            this.responseListener = other.responseListener;
        }
        return this;
    }

    @Override // com.samsung.android.sume.core.message.Message
    public Message post() {
        List<MediaBuffer> list;
        MediaBufferGroup$$ExternalSyntheticLambda2 mediaBufferGroup$$ExternalSyntheticLambda2;
        if (this.replyTo == null) {
            Log.d(TAG, "no reply object given for code " + getCode() + ", skip to send");
            return this;
        }
        try {
            try {
                Log.d(TAG, "send response: " + this);
                android.os.Message msg = toAndroidMessage();
                this.replyTo.send(msg);
            } catch (RemoteException e) {
                Log.w(TAG, "fail to send response: " + e.getMessage());
                if (this.bufferList != null) {
                    list = this.bufferList;
                    mediaBufferGroup$$ExternalSyntheticLambda2 =
                            new MediaBufferGroup$$ExternalSyntheticLambda2();
                }
            }
            if (this.bufferList != null) {
                list = this.bufferList;
                mediaBufferGroup$$ExternalSyntheticLambda2 =
                        new MediaBufferGroup$$ExternalSyntheticLambda2();
                list.forEach(mediaBufferGroup$$ExternalSyntheticLambda2);
            }
            this.bufferList = null;
            return this;
        } catch (Throwable th) {
            if (this.bufferList != null) {
                this.bufferList.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
            }
            this.bufferList = null;
            throw th;
        }
    }

    public Response post(Message.BundledDataHandler bundledDataHandler) {
        this.bundledDataHandler = bundledDataHandler;
        return (Response) post();
    }

    @Override // com.samsung.android.sume.core.message.Message
    public String toString() {
        return contentToString(
                this,
                new Supplier() { // from class:
                                 // com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return Response.this
                                .m9196lambda$toString$3$comsamsungandroidsumecoremessageResponse();
                    }
                });
    }

    static /* synthetic */ String lambda$toString$2(List it) {
        return "buffer-list=" + Arrays.toString(it.toArray());
    }

    /* renamed from: lambda$toString$3$com-samsung-android-sume-core-message-Response, reason: not valid java name */
    /* synthetic */ String m9196lambda$toString$3$comsamsungandroidsumecoremessageResponse() {
        return (String)
                Optional.ofNullable(this.bufferList)
                        .map(
                                new Function() { // from class:
                                                 // com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda3
                                    @Override // java.util.function.Function
                                    public final Object apply(Object obj) {
                                        return Response.lambda$toString$2((List) obj);
                                    }
                                })
                        .orElse("");
    }

    public Consumer<Message> getResponseListener() {
        return this.responseListener;
    }

    public static Response of(Message message) {
        return new Response(message);
    }

    public static Response of(int code) {
        return new Response(code);
    }

    public static Response of(int code, String key, Object data) {
        return (Response) new Response(code).put(key, data);
    }

    public static Response of(int code, Exception exception) {
        return (Response) new Response(code).setException(exception);
    }

    public static Response of(android.os.Message msg) {
        return new Response(msg);
    }

    static final class ListenerManager {
        private static volatile ListenerManager sInstance;
        private final Map<Integer, Consumer<Message>> consumerMap = new ConcurrentHashMap();

        public static ListenerManager getInstance() {
            if (sInstance == null) {
                synchronized (ListenerManager.class) {
                    if (sInstance == null) {
                        sInstance = new ListenerManager();
                    }
                }
            }
            return sInstance;
        }

        private ListenerManager() {}

        int register(Consumer<Message> consumer) {
            int id = consumer.hashCode();
            this.consumerMap.put(Integer.valueOf(id), consumer);
            return id;
        }

        Consumer<Message> unRegister(int id) {
            return this.consumerMap.remove(Integer.valueOf(id));
        }

        void clear() {
            this.consumerMap.clear();
        }
    }
}
