package com.samsung.android.sume.core.message;

import android.content.ContentValues;
import android.p009os.Bundle;
import android.p009os.Messenger;
import android.p009os.Parcelable;
import android.p009os.RemoteException;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup$$ExternalSyntheticLambda6;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public final class Request extends Message {
  private static final String TAG = Def.tagOf((Class<?>) Request.class);
  private List<MediaBuffer> inputBufferList;
  private boolean isOneWay;
  private List<MediaBuffer> outputBufferList;
  private Messenger receiver;

  private Request(android.p009os.Message message) {
    super(message);
    this.isOneWay = false;
    Bundle bundle = message.getData();
    Parcelable[] inputBuffers = bundle.getParcelableArray("input-buffer-list");
    if (inputBuffers != null) {
      this.inputBufferList =
          (List)
              Arrays.stream(inputBuffers)
                  .map(
                      new Function() { // from class:
                                       // com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          return Request.lambda$new$0((Parcelable) obj);
                        }
                      })
                  .collect(Collectors.toList());
    }
    Parcelable[] outputBuffers = bundle.getParcelableArray("output-buffer-list");
    if (outputBuffers != null) {
      this.outputBufferList =
          (List)
              Arrays.stream(outputBuffers)
                  .map(
                      new Function() { // from class:
                                       // com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          return Request.lambda$new$1((Parcelable) obj);
                        }
                      })
                  .collect(Collectors.toList());
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
  public android.p009os.Message toAndroidMessage() {
    android.p009os.Message msg = super.toAndroidMessage();
    Bundle bundle = msg.getData();
    List<MediaBuffer> list = this.inputBufferList;
    if (list != null) {
      bundle.putParcelableArray(
          "input-buffer-list", (Parcelable[]) list.toArray(new MediaBuffer[0]));
    }
    List<MediaBuffer> list2 = this.outputBufferList;
    if (list2 != null) {
      bundle.putParcelableArray(
          "output-buffer-list", (Parcelable[]) list2.toArray(new MediaBuffer[0]));
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
    MediaBufferGroup$$ExternalSyntheticLambda6 mediaBufferGroup$$ExternalSyntheticLambda6;
    if (this.receiver == null) {
      Log.m94d(TAG, "no receiver object given for code " + getCode() + ", skip to send");
      return this;
    }
    try {
      try {
        try {
          Log.m94d(TAG, "send request: " + this);
          this.receiver.send(toAndroidMessage());
          List<MediaBuffer> list2 = this.inputBufferList;
          if (list2 != null) {
            list2.forEach(new MediaBufferGroup$$ExternalSyntheticLambda6());
          }
          list = this.outputBufferList;
        } catch (RemoteException e) {
          Log.m102w(TAG, "fail to send request: " + e.getMessage());
          List<MediaBuffer> list3 = this.inputBufferList;
          if (list3 != null) {
            list3.forEach(new MediaBufferGroup$$ExternalSyntheticLambda6());
          }
          list = this.outputBufferList;
          if (list != null) {
            mediaBufferGroup$$ExternalSyntheticLambda6 =
                new MediaBufferGroup$$ExternalSyntheticLambda6();
          }
        }
      } catch (Exception e2) {
        Log.m102w(TAG, "Exception: " + e2.getMessage());
        e2.printStackTrace();
        List<MediaBuffer> list4 = this.inputBufferList;
        if (list4 != null) {
          list4.forEach(new MediaBufferGroup$$ExternalSyntheticLambda6());
        }
        list = this.outputBufferList;
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
      this.inputBufferList = null;
      this.outputBufferList = null;
      return this;
    } catch (Throwable th) {
      List<MediaBuffer> list5 = this.inputBufferList;
      if (list5 != null) {
        list5.forEach(new MediaBufferGroup$$ExternalSyntheticLambda6());
      }
      List<MediaBuffer> list6 = this.outputBufferList;
      if (list6 != null) {
        list6.forEach(new MediaBufferGroup$$ExternalSyntheticLambda6());
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
    return (MediaBuffer)
        Optional.ofNullable(this.inputBufferList)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda2
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return Request.lambda$getInputBuffer$2((List) obj);
                  }
                })
            .orElse(null);
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
    return (MediaBuffer)
        Optional.ofNullable(this.outputBufferList)
            .map(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda3
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    return Request.lambda$getOutputBuffer$3((List) obj);
                  }
                })
            .orElse(null);
  }

  static /* synthetic */ MediaBuffer lambda$getOutputBuffer$3(List it) {
    if (it.isEmpty()) {
      return null;
    }
    return it.size() == 1 ? (MediaBuffer) it.get(0) : MediaBuffer.groupOf((List<MediaBuffer>) it);
  }

  public ContentValues getContentValues() {
    return (ContentValues)
        Optional.ofNullable(getContentValuesList())
            .flatMap(
                new Function() { // from class:
                                 // com.samsung.android.sume.core.message.Request$$ExternalSyntheticLambda4
                  @Override // java.util.function.Function
                  public final Object apply(Object obj) {
                    Optional findFirst;
                    findFirst = ((List) obj).stream().findFirst();
                    return findFirst;
                  }
                })
            .orElse(null);
  }

  @Override // com.samsung.android.sume.core.message.Message
  public String toString() {
    return contentToString();
  }

  public List<ContentValues> getContentValuesList() {
    return (List) get("content-values");
  }

  /* renamed from: of */
  public static Request m433of(int code) {
    return new Request(code);
  }

  /* renamed from: of */
  public static Request m434of(int code, String key, Object data) {
    return (Request) new Request(code).put(key, data);
  }

  /* renamed from: of */
  public static Request m435of(android.p009os.Message msg) {
    return new Request(msg);
  }
}
