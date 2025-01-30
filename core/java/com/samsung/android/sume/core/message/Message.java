package com.samsung.android.sume.core.message;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.p009os.Bundle;
import android.p009os.Messenger;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.GenericMediaBuffer;
import com.samsung.android.sume.core.descriptor.p044nn.NNDescriptor$$ExternalSyntheticLambda0;
import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class Message {
  public static final int BLOCK_DONE = 506;
  public static final int BLOCK_START = 505;
  public static final int CACHE_DATA = 6;
  public static final int CODEC_NUM_WHOLE_FRAMES = 2;
  public static final int CODEC_OUTPUT_FORMAT_CHANGED = 3;
  public static final int CONTENT_DONE = 510;
  public static final int CONTENT_START = 509;
  public static final int CREATE_GRAPH = 900;
  public static final int CREATE_MEDIAFILTER_CONTROLLER = 905;
  public static final int CUSTOM_REQUEST = 989;
  public static final int END_OF_STREAM = 5;
  public static final int ERROR = 993;
  public static final int ERROR_DEAD_OBJECT = -5;
  public static final int ERROR_MALFORMED = -2;
  public static final int ERROR_SERVICE_DEAD = -4;
  public static final int ERROR_TIMEOUT = -1;
  public static final int ERROR_UNSUPPORTED = -3;
  public static final int EVENT = 990;
  public static final int FRAME_DONE = 508;
  public static final int FRAME_START = 507;
  public static final int GLOBAL_FINISH = 502;
  public static final int GLOBAL_START = 501;
  public static final int INFO = 994;
  public static final String KEY_BLOCK_ID = "block-id";
  public static final String KEY_CACHE_ID = "cache-id";
  public static final String KEY_CONTENTS_ID = "contents-id";
  public static final String KEY_CONTROLLER_ID = "controller-id";
  public static final String KEY_DISPLAY_NAME = "display-name";
  public static final String KEY_DURATION_MS = "duration";
  public static final String KEY_END_TIME_MS = "end-time-ms";
  public static final String KEY_END_TIME_US = "end-time-us";
  public static final String KEY_FILE_DESCRIPTOR = "file-descriptor";
  public static final String KEY_HEIGHT = "height";
  public static final String KEY_ID = "id";
  public static final String KEY_IN_BUFFER = "input-buffer";
  public static final String KEY_IN_FILE = "input-file";
  public static final String KEY_MEDIA_TYPE = "media-type";
  public static final String KEY_MESSAGE = "message";
  public static final String KEY_NUM_BLOCKS = "num-blocks";
  public static final String KEY_NUM_UNITS = "num-units";
  public static final String KEY_OUT_BUFFER = "output-buffer";
  public static final String KEY_OUT_FILE = "output-file";
  public static final String KEY_POSITION = "position";
  public static final String KEY_PROCESSED_FRAMES = "number-of-frames";
  public static final String KEY_ROTATION = "rotation-degrees";
  public static final String KEY_START_TIME_MS = "start-time-ms";
  public static final String KEY_START_TIME_US = "start-time-us";
  public static final String KEY_STATUS = "status-code";
  public static final String KEY_UNIT_DESCRIPTION = "unit-description";
  public static final String KEY_UNIT_ID = "unit-id";
  public static final String KEY_WHOLE_FRAMES = "whole-frames";
  public static final String KEY_WIDTH = "width";
  public static final int MF_PREPARE_BEG = 511;
  public static final int MF_PREPARE_END = 512;
  public static final int MF_RELEASE_BEG = 515;
  public static final int MF_RELEASE_END = 516;
  public static final int MF_RUN_BEG = 513;
  public static final int MF_RUN_END = 514;
  public static final int MUXER_CONFIGURE_DATA = 4;

  /* renamed from: OK */
  public static final int f3064OK = 0;
  public static final int PAUSE_GRAPH = 902;
  public static final int PROCESS_DATA = 901;
  public static final int RELEASE_GRAPH = 904;
  public static final int RELEASE_MEDIAFILTER_CONTROLLER = 906;
  public static final int REQUEST = 991;
  public static final int RESPONSE = 992;
  public static final int RESUME_GRAPH = 903;
  public static final int STARTED = 503;
  public static final int STOPPED = 504;
  private static final String TAG = Def.tagOf((Class<?>) Message.class);
  public static final int TRACE_DATA = 7;
  public static final int TRACK_FORMAT = 1;
  public static final int WARN = 995;
  public static final int WARN_CANCELED = 702;
  public static final int WARN_FILTER_OUT_CONTENT = 701;
  private static final int _END_OF_ERROR_TYPE_ = -999;
  private static final int _END_OF_EVENT_TYPE_ = 499;
  private static final int _END_OF_INFO_TYPE_ = 699;
  private static final int _END_OF_MESSAGE_TYPE_ = 999;
  private static final int _END_OF_REQUEST_TYPE_ = 989;
  private static final int _END_OF_WARN_TYPE_ = 899;
  private static final int _START_OF_ERROR_TYPE_ = -1;
  private static final int _START_OF_EVENT_TYPE_ = 0;
  private static final int _START_OF_INFO_TYPE_ = 500;
  private static final int _START_OF_MESSAGE_TYPE_ = 990;
  private static final int _START_OF_REQUEST_TYPE_ = 900;
  private static final int _START_OF_WARN_TYPE_ = 700;
  protected BundledDataHandler bundledDataHandler;
  protected int code;
  protected Map<String, Object> data;
  protected Exception exception;
  protected int extra;
  private WeakReference<MessagePublisher> messagePublisher;
  protected Messenger replyTo;
  private boolean requestToReply;
  private Consumer<Message> responseListener;
  protected int type;

  @FunctionalInterface
  public interface BundledDataHandler {
    void accept(Bundle bundle);
  }

  @Retention(RetentionPolicy.SOURCE)
  public @interface MessageType {}

  @Retention(RetentionPolicy.SOURCE)
  public @interface RequestType {}

  public Message(android.p009os.Message message) {
    this.data = new HashMap();
    Bundle bundle = message.getData();
    bundle.setClassLoader(GenericMediaBuffer.class.getClassLoader());
    HashMap<String, Object> data = (HashMap) bundle.getSerializable("data");
    if (data != null) {
      this.data = data;
    }
    Exception exception = (Exception) bundle.getSerializable("exception");
    if (exception != null) {
      this.exception = exception;
    }
    this.code = message.what;
    this.type = message.arg1;
    this.extra = message.arg2;
    this.replyTo = message.replyTo;
  }

  public Message setBundledDataHandler(BundledDataHandler bundledDataHandler) {
    this.bundledDataHandler = bundledDataHandler;
    return this;
  }

  public BundledDataHandler getBundledDataHandler() {
    return this.bundledDataHandler;
  }

  public android.p009os.Message toAndroidMessage() {
    Bundle bundle = new Bundle();
    bundle.putSerializable("data", (Serializable) this.data);
    Exception exc = this.exception;
    if (exc != null) {
      bundle.putSerializable("exception", exc);
    }
    android.p009os.Message message = new android.p009os.Message();
    message.what = this.code;
    message.arg1 = this.type;
    message.arg2 = this.extra;
    message.replyTo = this.replyTo;
    message.setData(bundle);
    BundledDataHandler bundledDataHandler = this.bundledDataHandler;
    if (bundledDataHandler != null) {
      bundledDataHandler.accept(bundle);
    }
    return message;
  }

  Message(int code) {
    this(typeOf(code), code);
  }

  Message(int type, int code) {
    this.data = new HashMap();
    Def.require(
        isValidCode(type, code),
        "invalid code(" + code + ") for message(" + type + NavigationBarInflaterView.KEY_CODE_END,
        new Object[0]);
    this.type = type;
    this.code = code;
  }

  Message(Message other) {
    this.data = new HashMap();
    this.type = other.type;
    this.code = other.code;
    this.extra = other.extra;
    this.replyTo = other.replyTo;
    this.exception = other.exception;
    this.data = other.data;
    this.bundledDataHandler = other.bundledDataHandler;
  }

  private boolean isValidCode(int type, int code) {
    final int typeOfCode = typeOf(code);
    switch (type) {
      case 990:
        return Stream.of((Object[]) new Integer[] {990, 994, 995, 993})
            .anyMatch(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.message.Message$$ExternalSyntheticLambda4
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return Message.lambda$isValidCode$1(typeOfCode, (Integer) obj);
                  }
                });
      case 991:
      default:
        return type == typeOfCode;
      case 992:
        return Stream.of((Object[]) new Integer[] {992, 990, 991, 993, 995})
            .anyMatch(
                new Predicate() { // from class:
                                  // com.samsung.android.sume.core.message.Message$$ExternalSyntheticLambda3
                  @Override // java.util.function.Predicate
                  public final boolean test(Object obj) {
                    return Message.lambda$isValidCode$0(typeOfCode, (Integer) obj);
                  }
                });
    }
  }

  static /* synthetic */ boolean lambda$isValidCode$0(int typeOfCode, Integer it) {
    return it.intValue() == typeOfCode;
  }

  static /* synthetic */ boolean lambda$isValidCode$1(int typeOfCode, Integer it) {
    return it.intValue() == typeOfCode;
  }

  public int getCode() {
    return this.code;
  }

  public Map<String, Object> get() {
    return this.data;
  }

  public boolean contains(String key) {
    return this.data.containsKey(key);
  }

  public boolean containsAll(String... keys) {
    return Arrays.stream(keys)
        .allMatch(
            new Predicate() { // from class:
                              // com.samsung.android.sume.core.message.Message$$ExternalSyntheticLambda2
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                return Message.this.m426xc0a52838((String) obj);
              }
            });
  }

  /* renamed from: lambda$containsAll$2$com-samsung-android-sume-core-message-Message */
  /* synthetic */ boolean m426xc0a52838(String it) {
    return this.data.containsKey(it);
  }

  public boolean containsAny(String... keys) {
    return Arrays.stream(keys)
        .anyMatch(
            new Predicate() { // from class:
                              // com.samsung.android.sume.core.message.Message$$ExternalSyntheticLambda1
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                return Message.this.m427x136f6cc4((String) obj);
              }
            });
  }

  /* renamed from: lambda$containsAny$3$com-samsung-android-sume-core-message-Message */
  /* synthetic */ boolean m427x136f6cc4(String it) {
    return this.data.containsKey(it);
  }

  public <T> T get(String str) {
    return (T) this.data.get(str);
  }

  public <T> T get(String str, T t) {
    return (T) this.data.getOrDefault(str, t);
  }

  public <T> T remove(String str) {
    return (T) this.data.remove(str);
  }

  public Message put(Map<String, Object> data) {
    this.data.putAll(data);
    return this;
  }

  public Message put(String key, Object data) {
    this.data.put(key, data);
    return this;
  }

  public Message setException(Exception exception) {
    this.exception = exception;
    return this;
  }

  public Exception getException() {
    return this.exception;
  }

  public Message setPublisher(MessagePublisher publisher) {
    this.messagePublisher = new WeakReference<>(publisher);
    return this;
  }

  public Messenger getResponseReceiver() {
    return this.replyTo;
  }

  public Message setResponseReceiver(Messenger messenger) {
    this.replyTo = messenger;
    return this;
  }

  public Message post() {
    MessagePublisher publisher = this.messagePublisher.get();
    if (publisher != null) {
      publisher
          .getChannels(this.code)
          .forEach(
              new Consumer() { // from class:
                               // com.samsung.android.sume.core.message.Message$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                  Message.this.m9217lambda$post$4$comsamsungandroidsumecoremessageMessage(
                      (MessageChannel) obj);
                }
              });
    }
    return this;
  }

  /* renamed from: lambda$post$4$com-samsung-android-sume-core-message-Message, reason: not valid java name */
  /* synthetic */ void m9217lambda$post$4$comsamsungandroidsumecoremessageMessage(
      MessageChannel it) {
    Log.m94d(TAG, "post: " + this.code + " to channel[" + it.getId() + "]: " + it);
    it.send(this);
  }

  public Message then(Consumer<Message> responseListener) {
    MessagePublisher publisher = this.messagePublisher.get();
    if (publisher != null) {
      this.responseListener = responseListener;
      this.requestToReply = true;
    }
    return this;
  }

  public void reply(String key, Object data) {
    Consumer<Message> consumer = this.responseListener;
    if (consumer != null) {
      consumer.accept(new Message(992, 0).put(key, data));
    }
  }

  public void reply(Map<String, Object> data) {
    Consumer<Message> consumer = this.responseListener;
    if (consumer != null) {
      consumer.accept(new Message(992, 0).put(data));
    }
  }

  public boolean isRequestToReply() {
    return this.requestToReply;
  }

  public boolean isError() {
    return this.type == 993
        || isError(this.code)
        || ((Integer) get(KEY_STATUS, -1)).intValue() == 993;
  }

  public boolean isErrorThen(Consumer<Integer> errorConsumer) {
    boolean error = isError();
    if (error) {
      errorConsumer.accept(Integer.valueOf(this.code));
    }
    return error;
  }

  public boolean isWarn() {
    return this.type == 995
        || isWarn(this.code)
        || ((Integer) get(KEY_STATUS, -1)).intValue() == 995;
  }

  public boolean isOk() {
    return isOk(this.code) || ((Integer) get(KEY_STATUS, -1)).intValue() == 0;
  }

  public static boolean isError(int code) {
    return Def.isRangeIn(code, -999, -1);
  }

  public static boolean isWarn(int code) {
    return Def.isRangeIn(code, 700, 899);
  }

  public static boolean isOk(int code) {
    return Def.isRangeIn(code, 0, 699) || Def.isRangeIn(code, 900, 989);
  }

  public static int typeOf(int code) {
    if (isError(code)) {
      return 993;
    }
    if (isWarn(code)) {
      return 995;
    }
    if (Def.isRangeIn(code, 500, 699)) {
      return 994;
    }
    if (Def.isRangeIn(code, 0, _END_OF_EVENT_TYPE_)) {
      return 990;
    }
    if (Def.isRangeIn(code, 900, 989)) {
      return 991;
    }
    throw new IllegalArgumentException("invalid message code: " + code);
  }

  public String toString() {
    return contentToString();
  }

  public String contentToString() {
    return contentToString(this, null);
  }

  public String contentToString(Object obj, Supplier<String> appended) {
    return Def.tagOf(Optional.ofNullable(obj).orElse(""))
        + Def.contentToStringln(
            "\t",
            NavigationBarInflaterView.SIZE_MOD_START
                + Def.contentToString(
                    "type=" + this.type,
                    "code=" + this.code,
                    "extra=" + this.extra,
                    "exception=" + this.exception,
                    "bundledDataHandler=" + this.bundledDataHandler)
                + NavigationBarInflaterView.SIZE_MOD_END,
            "data=" + this.data,
            (String)
                Optional.ofNullable(appended)
                    .map(new NNDescriptor$$ExternalSyntheticLambda0())
                    .orElse(""));
  }
}
