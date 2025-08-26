package com.samsung.android.sume.core.controller;

import android.app.PendingIntent$$ExternalSyntheticLambda2;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.ConditionVariable;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.controller.MediaController;
import com.samsung.android.sume.core.controller.MediaFilterController;
import com.samsung.android.sume.core.exception.ContentFilterOutException;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.graph.Graph;
import com.samsung.android.sume.core.message.BlockingMessageChannel;
import com.samsung.android.sume.core.message.ContentsInfo;
import com.samsung.android.sume.core.message.Event;
import com.samsung.android.sume.core.message.Message;
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

/* loaded from: classes6.dex */
public class MediaFilterController implements MediaController<Response>, MessageConsumer {
    private static final String TAG = Def.tagOf((Class<?>) MediaFilterController.class);
    private MediaController.OnEventListener eventListener;
    private final int id;
    private volatile Graph<MediaFilter> mediaFilterGraph;
    private final MessageSubscriberImpl messageSubscriber;
    protected AtomicInteger contentId = new AtomicInteger(1);
    private final Map<Integer, ContentsInfo> contentsInfoMap = new HashMap();
    private final ConditionVariable mfControllerSync = new ConditionVariable();

    @Override // com.samsung.android.sume.core.controller.MediaController
    public /* bridge */ /* synthetic */ Response run(List list, List list2) {
        return run((List<MediaBuffer>) list, (List<MediaBuffer>) list2);
    }

    public MediaFilterController(int i) {
        this.id = i;
        MessageSubscriberImpl messageSubscriberImpl = new MessageSubscriberImpl();
        this.messageSubscriber = messageSubscriberImpl;
        messageSubscriberImpl.addMessageConsumer(this);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.controller.MediaController
    public Response run(List<MediaBuffer> list, List<MediaBuffer> list2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m9541x3a333949((MediaBuffer) obj);
            }
        });
        MediaController.OnEventListener onEventListener = this.eventListener;
        if (onEventListener != null) {
            onEventListener.onEvent(Event.of(501, new AnonymousClass1(list, jCurrentTimeMillis)));
        }
        if (this.mediaFilterGraph == null) {
            this.mfControllerSync.block();
        }
        this.mediaFilterGraph.run(list, list2);
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        MediaController.OnEventListener onEventListener2 = this.eventListener;
        if (onEventListener2 != null) {
            onEventListener2.onEvent((Event) Event.of(502, "timestampMs", Long.valueOf(jCurrentTimeMillis2)).put("id", Integer.valueOf(this.id)));
        }
        String str = TAG;
        Log.d(str, "run X: processing total " + (jCurrentTimeMillis2 - jCurrentTimeMillis) + " ms[#" + list2.size() + NavigationBarInflaterView.SIZE_MOD_END);
        if (!list2.isEmpty()) {
            Response buffer = Response.of(0).setBuffer(list2);
            List list3 = (List) list2.stream().map(new Function() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return this.f$0.m9542xb8943d28((MediaBuffer) obj);
                }
            }).collect(Collectors.toList());
            Log.d(str, "buffer-list[" + list3.size() + "]: " + Arrays.toString(list3.toArray()));
            list2.clear();
            list2.addAll(list3);
            return buffer;
        }
        return Response.of(702);
    }

    /* renamed from: lambda$run$0$com-samsung-android-sume-core-controller-MediaFilterController, reason: not valid java name */
    /* synthetic */ void m9541x3a333949(MediaBuffer mediaBuffer) {
        int andIncrement = this.contentId.getAndIncrement();
        mediaBuffer.setExtra(Message.KEY_CONTENTS_ID, Integer.valueOf(andIncrement));
        ContentsInfo contentsInfo = new ContentsInfo();
        contentsInfo.setContentsId(andIncrement);
        if (mediaBuffer.containsExtra("show-progress") && ((Boolean) mediaBuffer.getExtra("show-progress")).booleanValue()) {
            contentsInfo.setData("show-progress", true);
        }
        if (mediaBuffer.containsExtra(Message.KEY_DISPLAY_NAME)) {
            contentsInfo.setData(Message.KEY_DISPLAY_NAME, mediaBuffer.getExtra(Message.KEY_DISPLAY_NAME));
        }
        contentsInfo.setOriginalDataType(mediaBuffer.getFormat().getDataType());
        contentsInfo.setOriginalColorFormat(mediaBuffer.getFormat().getColorFormat());
        this.contentsInfoMap.put(Integer.valueOf(andIncrement), contentsInfo);
    }

    /* renamed from: com.samsung.android.sume.core.controller.MediaFilterController$1, reason: invalid class name */
    class AnonymousClass1 extends HashMap<String, Object> {
        final /* synthetic */ long val$beginTs;
        final /* synthetic */ List val$inBuffers;

        AnonymousClass1(List list, long j) {
            this.val$inBuffers = list;
            this.val$beginTs = j;
            put("id", Integer.valueOf(MediaFilterController.this.id));
            put("contents-list", list.stream().map(new Function() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$1$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MediaFilterController.AnonymousClass1.lambda$new$0((MediaBuffer) obj);
                }
            }).collect(Collectors.toList()));
            put("timestampMs", Long.valueOf(j));
        }

        static /* synthetic */ Integer lambda$new$0(MediaBuffer mediaBuffer) {
            Integer num = (Integer) mediaBuffer.getExtra(Message.KEY_CONTENTS_ID);
            num.intValue();
            return num;
        }
    }

    /* renamed from: lambda$run$1$com-samsung-android-sume-core-controller-MediaFilterController, reason: not valid java name */
    /* synthetic */ MutableMediaBuffer m9542xb8943d28(MediaBuffer mediaBuffer) throws UnsupportedOperationException {
        Integer num = (Integer) mediaBuffer.getExtra(Message.KEY_CONTENTS_ID);
        int iIntValue = num.intValue();
        ContentsInfo contentsInfo = this.contentsInfoMap.get(num);
        MediaType mediaType = mediaBuffer.getFormat().getMediaType();
        String str = TAG;
        Log.d(str, "[#" + iIntValue + "]mediaType=" + mediaType + ", contentsInfo= refactoring");
        MutableMediaBuffer mutableMediaBufferMutableOf = MediaBuffer.mutableOf(mediaBuffer);
        if (!mediaType.isMetaData() && !mediaType.isScala() && !((Boolean) mutableMediaBufferMutableOf.getExtra("freezed", false)).booleanValue()) {
            Log.d(str, "convert to original format");
            ColorFormat colorFormat = mutableMediaBufferMutableOf.getFormat().getColorFormat();
            if (colorFormat != contentsInfo.getOriginalColorFormat()) {
                Log.d(str, Def.fmtstr("color-format of output(%s) is differ from one of input(%s)", colorFormat.name(), contentsInfo.getOriginalColorFormat().name()));
                MutableMediaFormat mutableMediaFormatMutableImageOf = MediaFormat.mutableImageOf(new Object[0]);
                mutableMediaFormatMutableImageOf.setColorFormat(contentsInfo.getOriginalColorFormat());
                MutableMediaBuffer mutableMediaBufferMutableOf2 = MediaBuffer.mutableOf(mutableMediaFormatMutableImageOf);
                UniImgp.ofCvtColor().run((MediaBuffer) mutableMediaBufferMutableOf, mutableMediaBufferMutableOf2);
                mutableMediaBufferMutableOf.put(mutableMediaBufferMutableOf2.get());
            }
            DataType dataType = mutableMediaBufferMutableOf.getFormat().getDataType();
            if (dataType != contentsInfo.getOriginalDataType()) {
                Log.d(str, Def.fmtstr("data-type of output(%s) is differ from one of input(%s)", dataType.name(), contentsInfo.getOriginalDataType().name()));
                MutableMediaFormat mutableMediaFormatMutableImageOf2 = MediaFormat.mutableImageOf(new Object[0]);
                mutableMediaFormatMutableImageOf2.setDataType(contentsInfo.getOriginalDataType());
                MutableMediaBuffer mutableMediaBufferMutableOf3 = MediaBuffer.mutableOf(mutableMediaFormatMutableImageOf2);
                UniImgp.ofCvtData().run((MediaBuffer) mutableMediaBufferMutableOf, mutableMediaBufferMutableOf3);
                mutableMediaBufferMutableOf.put(mutableMediaBufferMutableOf3.get());
            }
        }
        return mutableMediaBufferMutableOf;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.controller.MediaController
    public Response request(Request request) {
        Response responseOf = Response.of(request);
        switch (request.getCode()) {
            case 901:
                List<MediaBuffer> list = (List) Optional.ofNullable(request.getInputBuffer()).map(new Function() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return this.f$0.m9540xbf598303((MediaBuffer) obj);
                    }
                }).orElseGet(new PendingIntent$$ExternalSyntheticLambda2());
                String str = TAG;
                Log.d(str, "input-buffers[#" + list.size() + "]: " + list);
                List<MediaBuffer> list2 = (List) Optional.ofNullable(request.getOutputBuffer()).map(new Function() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return ((MediaBuffer) obj).asList();
                    }
                }).orElseGet(new PendingIntent$$ExternalSyntheticLambda2());
                Log.d(str, "output-buffers[#" + list2.size() + "]: " + list2);
                try {
                    responseOf.join(run(list, list2));
                    return responseOf;
                } catch (Exception e) {
                    e.printStackTrace();
                    return Response.of(-2, e);
                }
            case 902:
                this.mediaFilterGraph.pause();
                return responseOf;
            case 903:
                this.mediaFilterGraph.resume();
                return responseOf;
            default:
                throw new IllegalArgumentException("unknown request: " + request.getCode());
        }
    }

    /* renamed from: lambda$request$2$com-samsung-android-sume-core-controller-MediaFilterController, reason: not valid java name */
    /* synthetic */ List m9540xbf598303(MediaBuffer mediaBuffer) {
        return ((Boolean) mediaBuffer.getExtra("singular-buffer", false)).booleanValue() ? new ArrayList<MediaBuffer>(mediaBuffer) { // from class: com.samsung.android.sume.core.controller.MediaFilterController.2
            final /* synthetic */ MediaBuffer val$it;

            {
                this.val$it = mediaBuffer;
                add(mediaBuffer);
            }
        } : mediaBuffer.asList();
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
    public void setOnEventListener(MediaController.OnEventListener onEventListener) {
        this.eventListener = onEventListener;
    }

    public void setMediaFilterGraph(Graph<MediaFilter> graph) {
        this.mediaFilterGraph = graph;
        graph.setMessageSubscriber(this.messageSubscriber);
        this.mfControllerSync.open();
    }

    @Override // com.samsung.android.sume.core.message.MessageConsumer
    public int[] getConsumeMessage() {
        return new int[]{993, 501, 502, 509, 510, 505, 506, 507, 508, 2, 511, 512, 513, 514, 515, 516};
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0069  */
    @Override // com.samsung.android.sume.core.message.MessageConsumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onMessageReceived(final Message message) throws UnsupportedOperationException {
        Event event;
        String str = TAG;
        Log.d(str, "onMessageReceived: " + message);
        try {
            if (message.isError()) {
                Log.d(str, "error occur: " + message.getException());
                event = message.getException() instanceof ContentFilterOutException ? (Event) Event.of(701, (String) Optional.ofNullable(message.getException().getMessage()).orElse("none")).put("id", Integer.valueOf(this.id)) : null;
            }
            if (message.contains(Message.KEY_CONTENTS_ID) && IntStream.range(511, 516).boxed().noneMatch(new Predicate() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return MediaFilterController.lambda$onMessageReceived$3(message, (Integer) obj);
                }
            })) {
                Integer num = (Integer) message.get(Message.KEY_CONTENTS_ID);
                num.intValue();
                ContentsInfo contentsInfo = this.contentsInfoMap.get(num);
                contentsInfo.join(ContentsInfo.wrap(message));
                if (message.getCode() == 508 && contentsInfo.getAsBooleanOr("show-progress", false)) {
                    message.put("show-progress", true);
                    message.put(Message.KEY_WHOLE_FRAMES, Integer.valueOf(contentsInfo.getWholeFrames()));
                    message.put(Message.KEY_DISPLAY_NAME, contentsInfo.getDataOr(Message.KEY_DISPLAY_NAME, ""));
                }
            }
            MediaController.OnEventListener onEventListener = this.eventListener;
            if (onEventListener == null) {
                return false;
            }
            onEventListener.onEvent((Event) Optional.ofNullable(event).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda5
                @Override // java.util.function.Supplier
                public final Object get() {
                    return this.f$0.m9539xa2e3d127(message);
                }
            }));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static /* synthetic */ boolean lambda$onMessageReceived$3(Message message, Integer num) {
        return num.intValue() == message.getCode();
    }

    /* renamed from: lambda$onMessageReceived$4$com-samsung-android-sume-core-controller-MediaFilterController, reason: not valid java name */
    /* synthetic */ Event m9539xa2e3d127(Message message) {
        return (Event) Event.of(message).put("id", Integer.valueOf(this.id));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class MessageSubscriberImpl extends MessageSubscriberBase {
        private final Thread messageThread;

        public MessageSubscriberImpl() {
            super(new BlockingMessageChannel("MediaFilterController"));
            Thread thread = new Thread(new Runnable() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$MessageSubscriberImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.threadEntry();
                }
            });
            this.messageThread = thread;
            thread.start();
            ((BlockingMessageChannel) getMessageChannel()).setThreadWeakReference(new WeakReference<>(thread));
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void threadEntry() {
            while (true) {
                try {
                    onMessageReceived(getMessageChannel().receive());
                } catch (CancellationException unused) {
                    Log.d(MediaFilterController.TAG, "message channel is canceled");
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
