package com.samsung.android.sume.core.controller;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.ConditionVariable;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.ChannelRouterBase$$ExternalSyntheticLambda6;
import com.samsung.android.sume.core.exception.ContentFilterOutException;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.graph.Graph;
import com.samsung.android.sume.core.message.BlockingMessageChannel;
import com.samsung.android.sume.core.message.ContentsInfo;
import com.samsung.android.sume.core.message.Event;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.message.MessageChannel;
import com.samsung.android.sume.core.message.MessageConsumer;
import com.samsung.android.sume.core.message.MessageSubscriberBase;
import com.samsung.android.sume.core.message.Request;
import com.samsung.android.sume.core.message.Response;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.MediaType;
import com.samsung.android.sume.solution.filter.UniImgp;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/* loaded from: classes4.dex */
public class MediaFilterController implements MediaController<Response>, MessageConsumer {
  private static final String TAG = Def.tagOf((Class<?>) MediaFilterController.class);
  private MediaController.OnEventListener eventListener;

  /* renamed from: id */
  private final int f3050id;
  private volatile Graph<MediaFilter> mediaFilterGraph;
  private final MessageSubscriberImpl messageSubscriber;
  protected AtomicInteger contentId = new AtomicInteger(1);
  private final Map<Integer, ContentsInfo> contentsInfoMap = new HashMap();
  private final ConditionVariable mfControllerSync = new ConditionVariable();

  @Override // com.samsung.android.sume.core.controller.MediaController
  public /* bridge */ /* synthetic */ Response run(List list, List list2) {
    return run((List<MediaBuffer>) list, (List<MediaBuffer>) list2);
  }

  public MediaFilterController(int id) {
    this.f3050id = id;
    MessageSubscriberImpl messageSubscriberImpl = new MessageSubscriberImpl();
    this.messageSubscriber = messageSubscriberImpl;
    messageSubscriberImpl.addMessageConsumer(this);
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.android.sume.core.controller.MediaController
  public Response run(List<MediaBuffer> inBuffers, List<MediaBuffer> outBuffers) {
    long beginTs = System.currentTimeMillis();
    inBuffers.forEach(
        new Consumer() { // from class:
                         // com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda0
          @Override // java.util.function.Consumer
          public final void accept(Object obj) {
            MediaFilterController.this.m353x3a333949((MediaBuffer) obj);
          }
        });
    MediaController.OnEventListener onEventListener = this.eventListener;
    if (onEventListener != null) {
      onEventListener.onEvent(Event.m423of(501, new C52621(inBuffers, beginTs)));
    }
    if (this.mediaFilterGraph == null) {
      this.mfControllerSync.block();
    }
    this.mediaFilterGraph.run(inBuffers, outBuffers);
    long endTs = System.currentTimeMillis();
    MediaController.OnEventListener onEventListener2 = this.eventListener;
    if (onEventListener2 != null) {
      onEventListener2.onEvent(
          (Event)
              Event.m422of(502, "timestampMs", Long.valueOf(endTs))
                  .put("id", Integer.valueOf(this.f3050id)));
    }
    String str = TAG;
    Log.m94d(
        str,
        "run X: processing total "
            + (endTs - beginTs)
            + " ms[#"
            + outBuffers.size()
            + NavigationBarInflaterView.SIZE_MOD_END);
    if (!outBuffers.isEmpty()) {
      Response response = Response.m436of(0).setBuffer(outBuffers);
      List<MediaBuffer> buffers =
          (List)
              outBuffers.stream()
                  .map(
                      new Function() { // from class:
                                       // com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda1
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                          return MediaFilterController.this.m354xb8943d28((MediaBuffer) obj);
                        }
                      })
                  .collect(Collectors.toList());
      Log.m94d(str, "buffer-list[" + buffers.size() + "]: " + Arrays.toString(buffers.toArray()));
      outBuffers.clear();
      outBuffers.addAll(buffers);
      return response;
    }
    Response response2 = Response.m436of(702);
    return response2;
  }

  /* renamed from: lambda$run$0$com-samsung-android-sume-core-controller-MediaFilterController */
  /* synthetic */ void m353x3a333949(MediaBuffer it) {
    int id = this.contentId.getAndIncrement();
    it.setExtra(Message.KEY_CONTENTS_ID, Integer.valueOf(id));
    ContentsInfo contentsInfo = new ContentsInfo();
    contentsInfo.setContentsId(id);
    if (it.containsExtra("show-progress")
        && ((Boolean) it.getExtra("show-progress")).booleanValue()) {
      contentsInfo.setData("show-progress", true);
    }
    if (it.containsExtra(Message.KEY_DISPLAY_NAME)) {
      contentsInfo.setData(Message.KEY_DISPLAY_NAME, it.getExtra(Message.KEY_DISPLAY_NAME));
    }
    contentsInfo.setOriginalDataType(it.getFormat().getDataType());
    contentsInfo.setOriginalColorFormat(it.getFormat().getColorFormat());
    this.contentsInfoMap.put(Integer.valueOf(id), contentsInfo);
  }

  /* renamed from: com.samsung.android.sume.core.controller.MediaFilterController$1 */
  class C52621 extends HashMap<String, Object> {
    final /* synthetic */ long val$beginTs;
    final /* synthetic */ List val$inBuffers;

    C52621(List list, long j) {
      this.val$inBuffers = list;
      this.val$beginTs = j;
      put("id", Integer.valueOf(MediaFilterController.this.f3050id));
      put(
          "contents-list",
          list.stream()
              .map(
                  new Function() { // from class:
                                   // com.samsung.android.sume.core.controller.MediaFilterController$1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                      Integer valueOf;
                      valueOf =
                          Integer.valueOf(
                              ((Integer) ((MediaBuffer) obj).getExtra(Message.KEY_CONTENTS_ID))
                                  .intValue());
                      return valueOf;
                    }
                  })
              .collect(Collectors.toList()));
      put("timestampMs", Long.valueOf(j));
    }
  }

  /* renamed from: lambda$run$1$com-samsung-android-sume-core-controller-MediaFilterController */
  /* synthetic */ MutableMediaBuffer m354xb8943d28(MediaBuffer it) {
    int contentsId = ((Integer) it.getExtra(Message.KEY_CONTENTS_ID)).intValue();
    ContentsInfo contentsInfo = this.contentsInfoMap.get(Integer.valueOf(contentsId));
    MediaType mediaType = it.getFormat().getMediaType();
    String str = TAG;
    Log.m94d(str, "[#" + contentsId + "]mediaType=" + mediaType + ", contentsInfo= refactoring");
    MutableMediaBuffer buf = MediaBuffer.mutableOf(it);
    if (!mediaType.isMetaData()
        && !mediaType.isScala()
        && !((Boolean) buf.getExtra("freezed", false)).booleanValue()) {
      Log.m94d(str, "convert to original format");
      ColorFormat colorFormat = buf.getFormat().getColorFormat();
      if (colorFormat != contentsInfo.getOriginalColorFormat()) {
        Log.m94d(
            str,
            Def.fmtstr(
                "color-format of output(%s) is differ from one of input(%s)",
                colorFormat.name(), contentsInfo.getOriginalColorFormat().name()));
        MutableMediaFormat format = MediaFormat.mutableImageOf(new Object[0]);
        format.setColorFormat(contentsInfo.getOriginalColorFormat());
        MutableMediaBuffer obuf = MediaBuffer.mutableOf(format);
        UniImgp.ofCvtColor().run((MediaBuffer) buf, obuf);
        buf.put(obuf.get());
      }
      DataType dataType = buf.getFormat().getDataType();
      if (dataType != contentsInfo.getOriginalDataType()) {
        Log.m94d(
            str,
            Def.fmtstr(
                "data-type of output(%s) is differ from one of input(%s)",
                dataType.name(), contentsInfo.getOriginalDataType().name()));
        MutableMediaFormat format2 = MediaFormat.mutableImageOf(new Object[0]);
        format2.setDataType(contentsInfo.getOriginalDataType());
        MutableMediaBuffer obuf2 = MediaBuffer.mutableOf(format2);
        UniImgp.ofCvtData().run((MediaBuffer) buf, obuf2);
        buf.put(obuf2.get());
      }
    }
    return buf;
  }

  /* JADX WARN: Can't rename method to resolve collision */
  @Override // com.samsung.android.sume.core.controller.MediaController
  public Response request(Request request) {
    Response response = Response.m440of(request);
    switch (request.getCode()) {
      case 901:
        List<MediaBuffer> inBuffers =
            (List)
                Optional.ofNullable(request.getInputBuffer())
                    .map(
                        new Function() { // from class:
                                         // com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda2
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            return MediaFilterController.this.m352xbf598303((MediaBuffer) obj);
                          }
                        })
                    .orElseGet(new ChannelRouterBase$$ExternalSyntheticLambda6());
        String str = TAG;
        Log.m94d(str, "input-buffers[#" + inBuffers.size() + "]: " + inBuffers);
        List<MediaBuffer> outBuffers =
            (List)
                Optional.ofNullable(request.getOutputBuffer())
                    .map(
                        new Function() { // from class:
                                         // com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda3
                          @Override // java.util.function.Function
                          public final Object apply(Object obj) {
                            return ((MediaBuffer) obj).asList();
                          }
                        })
                    .orElseGet(new ChannelRouterBase$$ExternalSyntheticLambda6());
        Log.m94d(str, "output-buffers[#" + outBuffers.size() + "]: " + outBuffers);
        try {
          response.join(run(inBuffers, outBuffers));
          return response;
        } catch (Exception e) {
          e.printStackTrace();
          return Response.m437of(-2, e);
        }
      case 902:
        this.mediaFilterGraph.pause();
        return response;
      case 903:
        this.mediaFilterGraph.resume();
        return response;
      default:
        throw new IllegalArgumentException("unknown request: " + request.getCode());
    }
  }

  /* renamed from: lambda$request$2$com-samsung-android-sume-core-controller-MediaFilterController */
  /* synthetic */ List m352xbf598303(MediaBuffer it) {
    if (((Boolean) it.getExtra("singular-buffer", false)).booleanValue()) {
      return new ArrayList<MediaBuffer>(
          it) { // from class: com.samsung.android.sume.core.controller.MediaFilterController.2
        final /* synthetic */ MediaBuffer val$it;

        {
          this.val$it = it;
          add(it);
        }
      };
    }
    return it.asList();
  }

  @Override // com.samsung.android.sume.core.controller.MediaController
  public synchronized void release() {
    if (this.mediaFilterGraph != null) {
      this.mediaFilterGraph.release();
    }
    MessageSubscriberImpl messageSubscriberImpl = this.messageSubscriber;
    if (messageSubscriberImpl != null) {
      messageSubscriberImpl.release();
    }
  }

  @Override // com.samsung.android.sume.core.controller.MediaController
  public void setOnEventListener(MediaController.OnEventListener eventListener) {
    this.eventListener = eventListener;
  }

  public void setMediaFilterGraph(Graph<MediaFilter> mediaFilterGraph) {
    this.mediaFilterGraph = mediaFilterGraph;
    mediaFilterGraph.setMessageSubscriber(this.messageSubscriber);
    this.mfControllerSync.open();
  }

  @Override // com.samsung.android.sume.core.message.MessageConsumer
  public int[] getConsumeMessage() {
    return new int[] {993, 501, 502, 509, 510, 505, 506, 507, 508, 2, 511, 512, 513, 514, 515, 516};
  }

  @Override // com.samsung.android.sume.core.message.MessageConsumer
  public boolean onMessageReceived(final Message message) throws UnsupportedOperationException {
    String str = TAG;
    Log.m94d(str, "onMessageReceived: " + message);
    Event event = null;
    try {
      if (message.isError()) {
        Log.m94d(str, "error occur: " + message.getException());
        if (message.getException() instanceof ContentFilterOutException) {
          event =
              (Event)
                  Event.m421of(
                          701,
                          (String)
                              Optional.ofNullable(message.getException().getMessage())
                                  .orElse("none"))
                      .put("id", Integer.valueOf(this.f3050id));
        }
      }
      if (message.contains(Message.KEY_CONTENTS_ID)
          && IntStream.range(511, 516)
              .boxed()
              .noneMatch(
                  new Predicate() { // from class:
                                    // com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda4
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                      return MediaFilterController.lambda$onMessageReceived$3(
                          Message.this, (Integer) obj);
                    }
                  })) {
        int contentsId = ((Integer) message.get(Message.KEY_CONTENTS_ID)).intValue();
        ContentsInfo contentsInfo = this.contentsInfoMap.get(Integer.valueOf(contentsId));
        contentsInfo.join(ContentsInfo.wrap(message));
        if (message.getCode() == 508 && contentsInfo.getAsBooleanOr("show-progress", false)) {
          message.put("show-progress", true);
          message.put(Message.KEY_WHOLE_FRAMES, Integer.valueOf(contentsInfo.getWholeFrames()));
          message.put(
              Message.KEY_DISPLAY_NAME, contentsInfo.getDataOr(Message.KEY_DISPLAY_NAME, ""));
        }
      }
      MediaController.OnEventListener onEventListener = this.eventListener;
      if (onEventListener == null) {
        return false;
      }
      onEventListener.onEvent(
          (Event)
              Optional.ofNullable(event)
                  .orElseGet(
                      new Supplier() { // from class:
                                       // com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda5
                        @Override // java.util.function.Supplier
                        public final Object get() {
                          return MediaFilterController.this.m351xa2e3d127(message);
                        }
                      }));
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }

  static /* synthetic */ boolean lambda$onMessageReceived$3(Message message, Integer it) {
    return it.intValue() == message.getCode();
  }

  /* renamed from: lambda$onMessageReceived$4$com-samsung-android-sume-core-controller-MediaFilterController */
  /* synthetic */ Event m351xa2e3d127(Message message) {
    return (Event) Event.m424of(message).put("id", Integer.valueOf(this.f3050id));
  }

  /* JADX INFO: Access modifiers changed from: private */
  static final class MessageSubscriberImpl extends MessageSubscriberBase {
    private final Thread messageThread;

    public MessageSubscriberImpl() {
      super(new BlockingMessageChannel("MediaFilterController"));
      Thread thread =
          new Thread(
              new Runnable() { // from class:
                               // com.samsung.android.sume.core.controller.MediaFilterController$MessageSubscriberImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                  MediaFilterController.MessageSubscriberImpl.this.threadEntry();
                }
              });
      this.messageThread = thread;
      thread.start();
      BlockingMessageChannel bmc = (BlockingMessageChannel) getMessageChannel();
      bmc.setThreadWeakReference(new WeakReference<>(thread));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void threadEntry() {
      MessageChannel messageChannel = getMessageChannel();
      while (true) {
        try {
          Message message = messageChannel.receive();
          onMessageReceived(message);
        } catch (CancellationException e) {
          Log.m94d(MediaFilterController.TAG, "message channel is canceled");
          return;
        }
      }
    }

    @Override // com.samsung.android.sume.core.message.MessageSubscriberBase
    public void release() {
      getMessageChannel().cancel();
    }
  }
}
