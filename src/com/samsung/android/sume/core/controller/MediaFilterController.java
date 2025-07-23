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
import java.util.stream.Collectors;

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
        long currentTimeMillis = System.currentTimeMillis();
        list.forEach(new Consumer() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                MediaFilterController.this.m9528x3a333949((MediaBuffer) obj);
            }
        });
        MediaController.OnEventListener onEventListener = this.eventListener;
        if (onEventListener != null) {
            onEventListener.onEvent(Event.of(501, new AnonymousClass1(list, currentTimeMillis)));
        }
        if (this.mediaFilterGraph == null) {
            this.mfControllerSync.block();
        }
        this.mediaFilterGraph.run(list, list2);
        long currentTimeMillis2 = System.currentTimeMillis();
        MediaController.OnEventListener onEventListener2 = this.eventListener;
        if (onEventListener2 != null) {
            onEventListener2.onEvent((Event) Event.of(502, "timestampMs", Long.valueOf(currentTimeMillis2)).put("id", Integer.valueOf(this.id)));
        }
        String str = TAG;
        Log.d(str, "run X: processing total " + (currentTimeMillis2 - currentTimeMillis) + " ms[#" + list2.size() + NavigationBarInflaterView.SIZE_MOD_END);
        if (!list2.isEmpty()) {
            Response buffer = Response.of(0).setBuffer(list2);
            List list3 = (List) list2.stream().map(new Function() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return MediaFilterController.this.m9529xb8943d28((MediaBuffer) obj);
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
    /* synthetic */ void m9528x3a333949(MediaBuffer mediaBuffer) {
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
    /* synthetic */ MutableMediaBuffer m9529xb8943d28(MediaBuffer mediaBuffer) {
        Integer num = (Integer) mediaBuffer.getExtra(Message.KEY_CONTENTS_ID);
        int intValue = num.intValue();
        ContentsInfo contentsInfo = this.contentsInfoMap.get(num);
        MediaType mediaType = mediaBuffer.getFormat().getMediaType();
        String str = TAG;
        Log.d(str, "[#" + intValue + "]mediaType=" + mediaType + ", contentsInfo= refactoring");
        MutableMediaBuffer mutableOf = MediaBuffer.mutableOf(mediaBuffer);
        if (!mediaType.isMetaData() && !mediaType.isScala() && !((Boolean) mutableOf.getExtra("freezed", false)).booleanValue()) {
            Log.d(str, "convert to original format");
            ColorFormat colorFormat = mutableOf.getFormat().getColorFormat();
            if (colorFormat != contentsInfo.getOriginalColorFormat()) {
                Log.d(str, Def.fmtstr("color-format of output(%s) is differ from one of input(%s)", colorFormat.name(), contentsInfo.getOriginalColorFormat().name()));
                MutableMediaFormat mutableImageOf = MediaFormat.mutableImageOf(new Object[0]);
                mutableImageOf.setColorFormat(contentsInfo.getOriginalColorFormat());
                MutableMediaBuffer mutableOf2 = MediaBuffer.mutableOf(mutableImageOf);
                UniImgp.ofCvtColor().run((MediaBuffer) mutableOf, mutableOf2);
                mutableOf.put(mutableOf2.get());
            }
            DataType dataType = mutableOf.getFormat().getDataType();
            if (dataType != contentsInfo.getOriginalDataType()) {
                Log.d(str, Def.fmtstr("data-type of output(%s) is differ from one of input(%s)", dataType.name(), contentsInfo.getOriginalDataType().name()));
                MutableMediaFormat mutableImageOf2 = MediaFormat.mutableImageOf(new Object[0]);
                mutableImageOf2.setDataType(contentsInfo.getOriginalDataType());
                MutableMediaBuffer mutableOf3 = MediaBuffer.mutableOf(mutableImageOf2);
                UniImgp.ofCvtData().run((MediaBuffer) mutableOf, mutableOf3);
                mutableOf.put(mutableOf3.get());
            }
        }
        return mutableOf;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.samsung.android.sume.core.controller.MediaController
    public Response request(Request request) {
        Response of = Response.of(request);
        switch (request.getCode()) {
            case 901:
                List<MediaBuffer> list = (List) Optional.ofNullable(request.getInputBuffer()).map(new Function() { // from class: com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return MediaFilterController.this.m9527xbf598303((MediaBuffer) obj);
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
                    of.join(run(list, list2));
                    return of;
                } catch (Exception e) {
                    e.printStackTrace();
                    return Response.of(-2, e);
                }
            case 902:
                this.mediaFilterGraph.pause();
                return of;
            case 903:
                this.mediaFilterGraph.resume();
                return of;
            default:
                throw new IllegalArgumentException("unknown request: " + request.getCode());
        }
    }

    /* renamed from: lambda$request$2$com-samsung-android-sume-core-controller-MediaFilterController, reason: not valid java name */
    /* synthetic */ List m9527xbf598303(MediaBuffer mediaBuffer) {
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

    /* JADX WARN: Removed duplicated region for block: B:19:0x00d0 A[Catch: Exception -> 0x00e4, TRY_LEAVE, TryCatch #0 {Exception -> 0x00e4, blocks: (B:3:0x001e, B:5:0x0024, B:7:0x003f, B:8:0x006a, B:10:0x0071, B:12:0x0088, B:14:0x00a8, B:16:0x00ae, B:17:0x00cc, B:19:0x00d0), top: B:2:0x001e }] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00e3 A[RETURN] */
    @Override // com.samsung.android.sume.core.message.MessageConsumer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onMessageReceived(final com.samsung.android.sume.core.message.Message r9) throws java.lang.UnsupportedOperationException {
        /*
            r8 = this;
            java.lang.String r0 = "display-name"
            java.lang.String r1 = "show-progress"
            java.lang.String r2 = "contents-id"
            java.lang.String r3 = "error occur: "
            java.lang.String r4 = com.samsung.android.sume.core.controller.MediaFilterController.TAG
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "onMessageReceived: "
            r5.<init>(r6)
            r5.append(r9)
            java.lang.String r5 = r5.toString()
            android.util.Log.d(r4, r5)
            r5 = 0
            boolean r6 = r9.isError()     // Catch: java.lang.Exception -> Le4
            if (r6 == 0) goto L69
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Exception -> Le4
            r6.<init>(r3)     // Catch: java.lang.Exception -> Le4
            java.lang.Exception r3 = r9.getException()     // Catch: java.lang.Exception -> Le4
            r6.append(r3)     // Catch: java.lang.Exception -> Le4
            java.lang.String r3 = r6.toString()     // Catch: java.lang.Exception -> Le4
            android.util.Log.d(r4, r3)     // Catch: java.lang.Exception -> Le4
            java.lang.Exception r3 = r9.getException()     // Catch: java.lang.Exception -> Le4
            boolean r3 = r3 instanceof com.samsung.android.sume.core.exception.ContentFilterOutException     // Catch: java.lang.Exception -> Le4
            if (r3 == 0) goto L69
            java.lang.Exception r3 = r9.getException()     // Catch: java.lang.Exception -> Le4
            java.lang.String r3 = r3.getMessage()     // Catch: java.lang.Exception -> Le4
            java.util.Optional r3 = java.util.Optional.ofNullable(r3)     // Catch: java.lang.Exception -> Le4
            java.lang.String r4 = "none"
            java.lang.Object r3 = r3.orElse(r4)     // Catch: java.lang.Exception -> Le4
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Exception -> Le4
            r4 = 701(0x2bd, float:9.82E-43)
            com.samsung.android.sume.core.message.Event r3 = com.samsung.android.sume.core.message.Event.of(r4, r3)     // Catch: java.lang.Exception -> Le4
            java.lang.String r4 = "id"
            int r6 = r8.id     // Catch: java.lang.Exception -> Le4
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch: java.lang.Exception -> Le4
            com.samsung.android.sume.core.message.Message r3 = r3.put(r4, r6)     // Catch: java.lang.Exception -> Le4
            com.samsung.android.sume.core.message.Event r3 = (com.samsung.android.sume.core.message.Event) r3     // Catch: java.lang.Exception -> Le4
            goto L6a
        L69:
            r3 = 0
        L6a:
            boolean r4 = r9.contains(r2)     // Catch: java.lang.Exception -> Le4
            r6 = 1
            if (r4 == 0) goto Lcc
            r4 = 511(0x1ff, float:7.16E-43)
            r7 = 516(0x204, float:7.23E-43)
            java.util.stream.IntStream r4 = java.util.stream.IntStream.range(r4, r7)     // Catch: java.lang.Exception -> Le4
            java.util.stream.Stream r4 = r4.boxed()     // Catch: java.lang.Exception -> Le4
            com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda4 r7 = new com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda4     // Catch: java.lang.Exception -> Le4
            r7.<init>()     // Catch: java.lang.Exception -> Le4
            boolean r4 = r4.noneMatch(r7)     // Catch: java.lang.Exception -> Le4
            if (r4 == 0) goto Lcc
            java.lang.Object r2 = r9.get(r2)     // Catch: java.lang.Exception -> Le4
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch: java.lang.Exception -> Le4
            r2.intValue()     // Catch: java.lang.Exception -> Le4
            java.util.Map<java.lang.Integer, com.samsung.android.sume.core.message.ContentsInfo> r4 = r8.contentsInfoMap     // Catch: java.lang.Exception -> Le4
            java.lang.Object r2 = r4.get(r2)     // Catch: java.lang.Exception -> Le4
            com.samsung.android.sume.core.message.ContentsInfo r2 = (com.samsung.android.sume.core.message.ContentsInfo) r2     // Catch: java.lang.Exception -> Le4
            com.samsung.android.sume.core.message.ContentsInfo r4 = com.samsung.android.sume.core.message.ContentsInfo.wrap(r9)     // Catch: java.lang.Exception -> Le4
            r2.join(r4)     // Catch: java.lang.Exception -> Le4
            int r4 = r9.getCode()     // Catch: java.lang.Exception -> Le4
            r7 = 508(0x1fc, float:7.12E-43)
            if (r4 != r7) goto Lcc
            boolean r4 = r2.getAsBooleanOr(r1, r5)     // Catch: java.lang.Exception -> Le4
            if (r4 == 0) goto Lcc
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r6)     // Catch: java.lang.Exception -> Le4
            r9.put(r1, r4)     // Catch: java.lang.Exception -> Le4
            java.lang.String r1 = "whole-frames"
            int r4 = r2.getWholeFrames()     // Catch: java.lang.Exception -> Le4
            java.lang.Integer r4 = java.lang.Integer.valueOf(r4)     // Catch: java.lang.Exception -> Le4
            r9.put(r1, r4)     // Catch: java.lang.Exception -> Le4
            java.lang.String r1 = ""
            java.lang.Object r1 = r2.getDataOr(r0, r1)     // Catch: java.lang.Exception -> Le4
            r9.put(r0, r1)     // Catch: java.lang.Exception -> Le4
        Lcc:
            com.samsung.android.sume.core.controller.MediaController$OnEventListener r0 = r8.eventListener     // Catch: java.lang.Exception -> Le4
            if (r0 == 0) goto Le3
            java.util.Optional r1 = java.util.Optional.ofNullable(r3)     // Catch: java.lang.Exception -> Le4
            com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda5 r2 = new com.samsung.android.sume.core.controller.MediaFilterController$$ExternalSyntheticLambda5     // Catch: java.lang.Exception -> Le4
            r2.<init>()     // Catch: java.lang.Exception -> Le4
            java.lang.Object r8 = r1.orElseGet(r2)     // Catch: java.lang.Exception -> Le4
            com.samsung.android.sume.core.message.Event r8 = (com.samsung.android.sume.core.message.Event) r8     // Catch: java.lang.Exception -> Le4
            r0.onEvent(r8)     // Catch: java.lang.Exception -> Le4
            return r6
        Le3:
            return r5
        Le4:
            r8 = move-exception
            r8.printStackTrace()
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.sume.core.controller.MediaFilterController.onMessageReceived(com.samsung.android.sume.core.message.Message):boolean");
    }

    static /* synthetic */ boolean lambda$onMessageReceived$3(Message message, Integer num) {
        return num.intValue() == message.getCode();
    }

    /* renamed from: lambda$onMessageReceived$4$com-samsung-android-sume-core-controller-MediaFilterController, reason: not valid java name */
    /* synthetic */ Event m9526xa2e3d127(Message message) {
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
                    MediaFilterController.MessageSubscriberImpl.this.threadEntry();
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
