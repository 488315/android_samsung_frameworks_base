package com.samsung.android.photoremasterservice;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes5.dex */
public class ServiceID {
  public static final int MSG_DEINIT = 3;
  public static final int MSG_EXCEPTION = -1;
  public static final int MSG_GET_BITMAP_PARAM = 19;
  public static final int MSG_GET_FOCUS_ROI = 17;
  public static final int MSG_GET_INT_PARAM = 12;
  public static final int MSG_GET_LONG_PARAM = 13;
  public static final int MSG_GET_STRING_PARAM = 11;
  public static final int MSG_INIT = 2;
  public static final int MSG_ON_UPDATE_METADATA = 15;
  public static final int MSG_ON_UPDATE_PROGRESS = 16;

  @Deprecated public static final int MSG_PROCESS_AESTHETIC_SCORING = 7;
  public static final int MSG_PROCESS_IMAGE = 5;

  @Deprecated public static final int MSG_PROCESS_IMAGE_LEGACY = 6;
  public static final int MSG_RET = 0;
  public static final int MSG_SET_BITMAP_PARAM = 20;

  @Deprecated public static final int MSG_SET_CONTEXT = 1;
  public static final int MSG_SET_LONG_PARAM = 10;
  public static final int MSG_SET_PROGRESS_UPDATE_LISTENER = 14;
  public static final int MSG_SET_STRING_PARAM = 9;
  public static final int MSG_SET_URI_PARAM = 8;
  public static final int MSG_STOP = 4;
  public static final int MSG_TRY_INIT = 18;
  public static final String TAG = "PhotoRemasterServiceID";

  public enum Description {
    MSG_EXCEPTION(-1, "MSG_EXCEPTION"),
    MSG_RET(0, "MSG_RET"),
    MSG_SET_CONTEXT(1, "MSG_SET_CONTEXT"),
    MSG_INIT(2, "MSG_INIT"),
    MSG_TRY_INIT(18, "MSG_TRY_INIT"),
    MSG_DEINIT(3, "MSG_DEINIT"),
    MSG_STOP(4, "MSG_STOP"),
    MSG_PROCESS_IMAGE(5, "MSG_PROCESS_IMAGE"),
    MSG_PROCESS_IMAGE_LEGACY(6, "MSG_PROCESS_IMAGE_LEGACY"),
    MSG_PROCESS_AESTHETIC_SCORING(7, "MSG_PROCESS_AESTHETIC_SCORING"),
    MSG_SET_URI_PARAM(8, "MSG_SET_URI_PARAM"),
    MSG_SET_BITMAP_PARAM(20, "MSG_SET_BITMAP_PARAM"),
    MSG_SET_STRING_PARAM(9, "MSG_SET_STRING_PARAM"),
    MSG_SET_LONG_PARAM(10, "MSG_SET_LONG_PARAM"),
    MSG_GET_STRING_PARAM(11, "MSG_GET_STRING_PARAM"),
    MSG_GET_INT_PARAM(12, "MSG_GET_INT_PARAM"),
    MSG_GET_LONG_PARAM(13, "MSG_GET_LONG_PARAM"),
    MSG_SET_PROGRESS_UPDATE_LISTENER(14, "MSG_SET_PROGRESS_UPDATE_LISTENER"),
    MSG_ON_UPDATE_METADATA(15, "MSG_ON_UPDATE_METADATA"),
    MSG_ON_UPDATE_PROGRESS(16, "MSG_ON_UPDATE_PROGRESS"),
    MSG_GET_FOCUS_ROI(17, "MSG_GET_FOCUS_ROI"),
    MSG_GET_BITMAP_PARAM(19, "MSG_GET_BITMAP_PARAM");

    private static final Map<Integer, Description> SERVICE_ID_DESCRIPTION =
        Collections.unmodifiableMap(
            (Map)
                Stream.of((Object[]) values())
                    .collect(
                        Collectors.toMap(
                            new Function() { // from class:
                                             // com.samsung.android.photoremasterservice.ServiceID$Description$$ExternalSyntheticLambda1
                              @Override // java.util.function.Function
                              public final Object apply(Object obj) {
                                return Integer.valueOf(((ServiceID.Description) obj).getId());
                              }
                            },
                            Function.identity())));
    private final int code;
    private final String description;

    Description(int code, String description) {
      this.code = code;
      this.description = description;
    }

    public static Description createFromId(final int code) {
      return (Description)
          Optional.ofNullable(SERVICE_ID_DESCRIPTION.get(Integer.valueOf(code)))
              .orElseThrow(
                  new Supplier() { // from class:
                                   // com.samsung.android.photoremasterservice.ServiceID$Description$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                      return ServiceID.Description.lambda$createFromId$0(code);
                    }
                  });
    }

    static /* synthetic */ IllegalArgumentException lambda$createFromId$0(int code) {
      return new IllegalArgumentException("Invalid ID : " + code);
    }

    public int getId() {
      return this.code;
    }

    public String getDescription() {
      return this.description;
    }
  }
}
