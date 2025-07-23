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
        Bundle data = message.getData();
        Parcelable[] parcelableArray = data.getParcelableArray("input-buffer-list");
        if (parcelableArray != null) {
            this.inputBufferList = (List) Arrays.stream(parcelableArray).map(new Function() { // from class: com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Request.lambda$new$0((Parcelable) obj);
                }
            }).collect(Collectors.toList());
        }
        Parcelable[] parcelableArray2 = data.getParcelableArray("output-buffer-list");
        if (parcelableArray2 != null) {
            this.outputBufferList = (List) Arrays.stream(parcelableArray2).map(new Function() { // from class: com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Request.lambda$new$1((Parcelable) obj);
                }
            }).collect(Collectors.toList());
        }
    }

    static /* synthetic */ MediaBuffer lambda$new$0(Parcelable parcelable) {
        return (MediaBuffer) parcelable;
    }

    static /* synthetic */ MediaBuffer lambda$new$1(Parcelable parcelable) {
        return (MediaBuffer) parcelable;
    }

    private Request(int i) {
        super(991, i);
        this.isOneWay = false;
    }

    @Override // com.samsung.android.sume.core.message.Message
    public android.os.Message toAndroidMessage() {
        android.os.Message androidMessage = super.toAndroidMessage();
        Bundle data = androidMessage.getData();
        List<MediaBuffer> list = this.inputBufferList;
        if (list != null) {
            data.putParcelableArray("input-buffer-list", (Parcelable[]) list.toArray(new MediaBuffer[0]));
        }
        List<MediaBuffer> list2 = this.outputBufferList;
        if (list2 != null) {
            data.putParcelableArray("output-buffer-list", (Parcelable[]) list2.toArray(new MediaBuffer[0]));
        }
        return androidMessage;
    }

    public Request setInputBuffer(MediaBuffer... mediaBufferArr) {
        this.inputBufferList = Arrays.asList(mediaBufferArr);
        return this;
    }

    public Request setInputBuffer(List<MediaBuffer> list) {
        this.inputBufferList = list;
        return this;
    }

    public Request setOutputBuffer(MediaBuffer... mediaBufferArr) {
        this.outputBufferList = Arrays.asList(mediaBufferArr);
        return this;
    }

    public Request setOutputBuffer(List<MediaBuffer> list) {
        this.outputBufferList = list;
        return this;
    }

    public Messenger getReceiver() {
        return this.receiver;
    }

    public Request setReceiver(Messenger messenger) {
        this.receiver = messenger;
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
                try {
                    Log.d(TAG, "send request: " + this);
                    this.receiver.send(toAndroidMessage());
                    return this;
                } catch (Exception e) {
                    Log.w(TAG, "Exception: " + e.getMessage());
                    e.printStackTrace();
                    List<MediaBuffer> list2 = this.inputBufferList;
                    if (list2 != null) {
                        list2.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
                    }
                    list = this.outputBufferList;
                    if (list != null) {
                        mediaBufferGroup$$ExternalSyntheticLambda2 = new MediaBufferGroup$$ExternalSyntheticLambda2();
                        list.forEach(mediaBufferGroup$$ExternalSyntheticLambda2);
                    }
                    this.inputBufferList = null;
                    this.outputBufferList = null;
                    return this;
                }
            } catch (RemoteException e2) {
                Log.w(TAG, "fail to send request: " + e2.getMessage());
                List<MediaBuffer> list3 = this.inputBufferList;
                if (list3 != null) {
                    list3.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
                }
                list = this.outputBufferList;
                if (list != null) {
                    mediaBufferGroup$$ExternalSyntheticLambda2 = new MediaBufferGroup$$ExternalSyntheticLambda2();
                    list.forEach(mediaBufferGroup$$ExternalSyntheticLambda2);
                }
                this.inputBufferList = null;
                this.outputBufferList = null;
                return this;
            }
        } finally {
            List<MediaBuffer> list4 = this.inputBufferList;
            if (list4 != null) {
                list4.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
            }
            List<MediaBuffer> list5 = this.outputBufferList;
            if (list5 != null) {
                list5.forEach(new MediaBufferGroup$$ExternalSyntheticLambda2());
            }
            this.inputBufferList = null;
            this.outputBufferList = null;
        }
    }

    @Override // com.samsung.android.sume.core.message.Message
    public Message then(Consumer<Message> consumer) {
        this.extra = Response.ListenerManager.getInstance().register(consumer);
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

    static /* synthetic */ MediaBuffer lambda$getInputBuffer$2(List list) {
        if (list.isEmpty()) {
            return null;
        }
        if (list.size() == 1) {
            ((MediaBuffer) list.get(0)).setExtra("singular-buffer", true);
            return (MediaBuffer) list.get(0);
        }
        return MediaBuffer.groupOf((List<MediaBuffer>) list);
    }

    public MediaBuffer getOutputBuffer() {
        return (MediaBuffer) Optional.ofNullable(this.outputBufferList).map(new Function() { // from class: com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Request.lambda$getOutputBuffer$3((List) obj);
            }
        }).orElse(null);
    }

    static /* synthetic */ MediaBuffer lambda$getOutputBuffer$3(List list) {
        if (list.isEmpty()) {
            return null;
        }
        return list.size() == 1 ? (MediaBuffer) list.get(0) : MediaBuffer.groupOf((List<MediaBuffer>) list);
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

    public static Request of(int i) {
        return new Request(i);
    }

    public static Request of(int i, String str, Object obj) {
        return (Request) new Request(i).put(str, obj);
    }

    public static Request of(android.os.Message message) {
        return new Request(message);
    }
}
