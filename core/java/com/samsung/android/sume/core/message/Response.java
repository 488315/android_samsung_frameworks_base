package com.samsung.android.sume.core.message;

import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda6;
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

/* loaded from: classes4.dex */
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
                                       // com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda1
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
    Log.m94d(TAG, "toAndroidMessage");
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
                                 // com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda3
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return Response.this.m441xed6285dd((List) obj);
                  }
                })
            .orElse(null);
  }

  /* renamed from: lambda$getBuffer$1$com-samsung-android-sume-core-message-Response */
  /* synthetic */ MediaBuffer m441xed6285dd(List it) {
    if (it.isEmpty()) {
      return null;
    }
    return it.size() == 1 ? this.bufferList.get(0) : MediaBuffer.groupOf((List<MediaBuffer>) it);
  }

  List<MediaBuffer> getBufferList() {
    return this.bufferList;
  }

  public Response join(Response other) {
    if (other.isOk()) {
      List<MediaBuffer> list = other.bufferList;
      if (list != null) {
        List<MediaBuffer> list2 = this.bufferList;
        if (list2 == null) {
          this.bufferList = list;
        } else {
          list2.addAll(list);
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
    Consumer<Message> consumer = other.responseListener;
    if (consumer != null) {
      this.responseListener = consumer;
    }
    return this;
  }

  @Override // com.samsung.android.sume.core.message.Message
  public Message post() {
    List<MediaBuffer> list;
    MediaBufferGroup$$ExternalSyntheticLambda6 mediaBufferGroup$$ExternalSyntheticLambda6;
    if (this.replyTo == null) {
      Log.m94d(TAG, "no reply object given for code " + getCode() + ", skip to send");
      return this;
    }
    try {
      try {
        Log.m94d(TAG, "send response: " + this);
        android.os.Message msg = toAndroidMessage();
        this.replyTo.send(msg);
        list = this.bufferList;
      } catch (RemoteException e) {
        Log.m102w(TAG, "fail to send response: " + e.getMessage());
        list = this.bufferList;
        if (list != null) {
          mediaBufferGroup$$ExternalSyntheticLambda6 =
              new MediaBufferGroup$$ExternalSyntheticLambda6();
        }
      }
      if (list != null) {
        mediaBufferGroup$$ExternalSyntheticLambda6 =
            new MediaBufferGroup$$ExternalSyntheticLambda6();
        list.forEach(mediaBufferGroup$$ExternalSyntheticLambda6);
      }
      this.bufferList = null;
      return this;
    } catch (Throwable th) {
      List<MediaBuffer> list2 = this.bufferList;
      if (list2 != null) {
        list2.forEach(new MediaBufferGroup$$ExternalSyntheticLambda6());
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
                         // com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda2
          @Override // java.util.function.Supplier
          public final Object get() {
            return Response.this.m9218lambda$toString$3$comsamsungandroidsumecoremessageResponse();
          }
        });
  }

  static /* synthetic */ String lambda$toString$2(List it) {
    return "buffer-list=" + Arrays.toString(it.toArray());
  }

  /* renamed from: lambda$toString$3$com-samsung-android-sume-core-message-Response, reason: not valid java name */
  /* synthetic */ String m9218lambda$toString$3$comsamsungandroidsumecoremessageResponse() {
    return (String)
        Optional.ofNullable(this.bufferList)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.message.Response$$ExternalSyntheticLambda0
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

  /* renamed from: of */
  public static Response m440of(Message message) {
    return new Response(message);
  }

  /* renamed from: of */
  public static Response m436of(int code) {
    return new Response(code);
  }

  /* renamed from: of */
  public static Response m438of(int code, String key, Object data) {
    return (Response) new Response(code).put(key, data);
  }

  /* renamed from: of */
  public static Response m437of(int code, Exception exception) {
    return (Response) new Response(code).setException(exception);
  }

  /* renamed from: of */
  public static Response m439of(android.os.Message msg) {
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
