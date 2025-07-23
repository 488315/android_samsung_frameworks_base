package com.samsung.android.sume.core.message;

import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda2;
import com.samsung.android.sume.core.message.Message;
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

/* loaded from: classes6.dex */
public final class Response extends Message {
    private static final String TAG = Def.tagOf((Class<?>) Response.class);
    private List<MediaBuffer> bufferList;
    private Consumer<Message> responseListener;

    private Response(int i) {
        super(992, i);
    }

    private Response(android.os.Message message) {
        super(message);
        Parcelable[] parcelableArray = message.getData().getParcelableArray("buffer-list");
        if (parcelableArray != null) {
            this.bufferList = (List) Arrays.stream(parcelableArray).map(new Function() { // from class: com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Response.lambda$new$0((Parcelable) obj);
                }
            }).collect(Collectors.toList());
        }
        if (this.extra != 0) {
            this.responseListener = ListenerManager.getInstance().unRegister(this.extra);
        }
    }

    static /* synthetic */ MediaBuffer lambda$new$0(Parcelable parcelable) {
        return (MediaBuffer) parcelable;
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
        android.os.Message androidMessage = super.toAndroidMessage();
        if (this.bufferList != null) {
            androidMessage.getData().putParcelableArray("buffer-list", (Parcelable[]) this.bufferList.toArray(new MediaBuffer[0]));
        }
        return androidMessage;
    }

    public Response setBuffer(MediaBuffer... mediaBufferArr) {
        this.bufferList = Arrays.asList(mediaBufferArr);
        return this;
    }

    public Response setBuffer(List<MediaBuffer> list) {
        this.bufferList = list;
        return this;
    }

    public MediaBuffer getBuffer() {
        return (MediaBuffer) Optional.ofNullable(this.bufferList).map(new Function() { // from class: com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Response.this.m9596xed6285dd((List) obj);
            }
        }).orElse(null);
    }

    /* renamed from: lambda$getBuffer$1$com-samsung-android-sume-core-message-Response, reason: not valid java name */
    /* synthetic */ MediaBuffer m9596xed6285dd(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.size() == 1 ? this.bufferList.get(0) : MediaBuffer.groupOf((List<MediaBuffer>) list);
    }

    List<MediaBuffer> getBufferList() {
        return this.bufferList;
    }

    public Response join(Response response) {
        if (response.isOk()) {
            List<MediaBuffer> list = response.bufferList;
            if (list != null) {
                List<MediaBuffer> list2 = this.bufferList;
                if (list2 == null) {
                    this.bufferList = list;
                } else {
                    list2.addAll(list);
                }
            }
            this.data.putAll(response.data);
        } else {
            this.code = response.code;
            this.type = response.type;
            if (response.exception != null) {
                this.exception = response.exception;
            }
        }
        if (response.extra > 0) {
            this.extra = response.extra;
        }
        if (response.replyTo != null) {
            this.replyTo = response.replyTo;
        }
        Consumer<Message> consumer = response.responseListener;
        if (consumer != null) {
            this.responseListener = consumer;
        }
        return this;
    }

    @Override // com.samsung.android.sume.core.message.Message
    public Message post() {
        if (this.replyTo == null) {
            Log.d(TAG, "no reply object given for code " + getCode() + ", skip to send");
            return this;
        }
        try {
            try {
                Log.d(TAG, "send response: " + this);
                this.replyTo.send(toAndroidMessage());
                List<MediaBuffer> list = this.bufferList;
                if (list != null) {
                    list.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
                }
                this.bufferList = null;
                return this;
            } catch (RemoteException e) {
                Log.w(TAG, "fail to send response: " + e.getMessage());
                List<MediaBuffer> list2 = this.bufferList;
                if (list2 != null) {
                    list2.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
                }
                this.bufferList = null;
                return this;
            }
        } catch (Throwable th) {
            List<MediaBuffer> list3 = this.bufferList;
            if (list3 != null) {
                list3.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
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
        return contentToString(this, new Supplier() { // from class: com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Response.this.m9597lambda$toString$3$comsamsungandroidsumecoremessageResponse();
            }
        });
    }

    static /* synthetic */ String lambda$toString$2(List list) {
        return "buffer-list=" + Arrays.toString(list.toArray());
    }

    /* renamed from: lambda$toString$3$com-samsung-android-sume-core-message-Response, reason: not valid java name */
    /* synthetic */ String m9597lambda$toString$3$comsamsungandroidsumecoremessageResponse() {
        return (String) Optional.ofNullable(this.bufferList).map(new Function() { // from class: com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Response.lambda$toString$2((List) obj);
            }
        }).orElse("");
    }

    public Consumer<Message> getResponseListener() {
        return this.responseListener;
    }

    public static Response of(Message message) {
        return new Response(message);
    }

    public static Response of(int i) {
        return new Response(i);
    }

    public static Response of(int i, String str, Object obj) {
        return (Response) new Response(i).put(str, obj);
    }

    public static Response of(int i, Exception exc) {
        return (Response) new Response(i).setException(exc);
    }

    public static Response of(android.os.Message message) {
        return new Response(message);
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

        private ListenerManager() {
        }

        int register(Consumer<Message> consumer) {
            int hashCode = consumer.hashCode();
            this.consumerMap.put(Integer.valueOf(hashCode), consumer);
            return hashCode;
        }

        Consumer<Message> unRegister(int i) {
            return this.consumerMap.remove(Integer.valueOf(i));
        }

        void clear() {
            this.consumerMap.clear();
        }
    }
}
