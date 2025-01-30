package com.samsung.android.sume.core.message;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import java.util.Map;

/* loaded from: classes4.dex */
public class Event extends Message {
  private static final String TAG = Def.tagOf((Class<?>) Event.class);

  Event(int code) {
    super(990, code);
  }

  Event(Message message) {
    super(message);
  }

  /* renamed from: of */
  public static Event m419of(int code) {
    return new Event(code);
  }

  /* renamed from: of */
  public static Event m421of(int code, String message) {
    return (Event) new Event(code).put("message", message);
  }

  /* renamed from: of */
  public static Event m420of(int code, Exception exception) {
    return (Event) new Event(code).setException(exception);
  }

  /* renamed from: of */
  public static Event m422of(int code, String key, Object data) {
    return (Event) new Event(code).put(key, data);
  }

  /* renamed from: of */
  public static Event m423of(int code, Map<String, Object> data) {
    return (Event) new Event(code).put(data);
  }

  /* renamed from: of */
  public static Event m425of(Response response) {
    Event event = new Event(response);
    if (response.getBufferList() != null && !response.getBufferList().isEmpty()) {
      Log.m94d(TAG, "response contains buffer-list, set it into event");
      event.put("buffer-list", response.getBufferList());
    }
    return event;
  }

  /* renamed from: of */
  public static Event m424of(Message message) {
    return new Event(message);
  }
}
