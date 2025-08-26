package com.android.app.motiontool;

import android.ddm.DdmHandle;
import android.util.Log;
import android.view.View;
import com.android.app.motiontool.BeginTraceResponse;
import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.app.motiontool.EndTraceResponse;
import com.android.app.motiontool.ErrorResponse;
import com.android.app.motiontool.HandshakeResponse;
import com.android.app.motiontool.MotionToolsResponse;
import com.android.app.motiontool.PollTraceResponse;
import com.android.app.viewcapture.data.MotionWindowData;
import com.google.protobuf.InvalidProtocolBufferException;
import java.util.LinkedHashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.apache.harmony.dalvik.ddmc.Chunk;
import org.apache.harmony.dalvik.ddmc.ChunkHandler;

/* loaded from: classes.dex */
public final class DdmHandleMotionTool extends DdmHandle {
    public static DdmHandleMotionTool INSTANCE;
    public final MotionToolManager motionToolManager;
    public static final Companion Companion = new Companion(null);
    public static final int CHUNK_MOTO = ChunkHandler.type("MOTO");

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ DdmHandleMotionTool(MotionToolManager motionToolManager, DefaultConstructorMarker defaultConstructorMarker) {
        this(motionToolManager);
    }

    public static void tryCatchingMotionToolManagerExceptions(MotionToolsResponse.Builder builder, Function0 function0) {
        try {
            function0.invoke();
        } catch (UnknownTraceIdException e) {
            int traceId = e.getTraceId();
            ErrorResponse.Builder builderNewBuilder = ErrorResponse.newBuilder();
            ErrorResponse.Code code = ErrorResponse.Code.UNKNOWN_TRACE_ID;
            builderNewBuilder.copyOnWrite();
            ErrorResponse.access$100((ErrorResponse) builderNewBuilder.instance, code);
            builderNewBuilder.copyOnWrite();
            ErrorResponse.access$300((ErrorResponse) builderNewBuilder.instance, "No running Trace found with traceId " + traceId);
            builder.setError(builderNewBuilder);
        } catch (WindowNotFoundException e2) {
            String windowId = e2.getWindowId();
            ErrorResponse.Builder builderNewBuilder2 = ErrorResponse.newBuilder();
            ErrorResponse.Code code2 = ErrorResponse.Code.WINDOW_NOT_FOUND;
            builderNewBuilder2.copyOnWrite();
            ErrorResponse.access$100((ErrorResponse) builderNewBuilder2.instance, code2);
            builderNewBuilder2.copyOnWrite();
            ErrorResponse.access$300((ErrorResponse) builderNewBuilder2.instance, "No window found with windowId " + windowId);
            builder.setError(builderNewBuilder2);
        }
    }

    public final Chunk handleChunk(Chunk chunk) {
        boolean z;
        MotionToolsResponse motionToolsResponse;
        try {
            MotionToolsRequest from = MotionToolsRequest.parseFrom(DdmHandle.wrapChunk(chunk).array());
            int number = from.getTypeCase().getNumber();
            if (number == 1) {
                HandshakeRequest handshake = from.getHandshake();
                MotionToolManager motionToolManager = this.motionToolManager;
                WindowIdentifier window = handshake.getWindow();
                synchronized (motionToolManager) {
                    z = motionToolManager.windowManagerGlobal.getRootView(window.getRootWindow()) != null;
                }
                HandshakeResponse.Status status = z ? HandshakeResponse.Status.OK : HandshakeResponse.Status.WINDOW_NOT_FOUND;
                MotionToolsResponse.Builder builderNewBuilder = MotionToolsResponse.newBuilder();
                HandshakeResponse.Builder builderNewBuilder2 = HandshakeResponse.newBuilder();
                builderNewBuilder2.copyOnWrite();
                HandshakeResponse.access$300((HandshakeResponse) builderNewBuilder2.instance);
                builderNewBuilder2.copyOnWrite();
                HandshakeResponse.access$100((HandshakeResponse) builderNewBuilder2.instance, status);
                builderNewBuilder.copyOnWrite();
                MotionToolsResponse.access$500((MotionToolsResponse) builderNewBuilder.instance, (HandshakeResponse) builderNewBuilder2.build());
                motionToolsResponse = (MotionToolsResponse) builderNewBuilder.build();
            } else if (number == 2) {
                final BeginTraceRequest beginTrace = from.getBeginTrace();
                final MotionToolsResponse.Builder builderNewBuilder3 = MotionToolsResponse.newBuilder();
                builderNewBuilder3.getClass();
                final int i = 0;
                tryCatchingMotionToolManagerExceptions(builderNewBuilder3, new Function0() { // from class: com.android.app.motiontool.DdmHandleMotionTool$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i2;
                        MotionWindowData motionWindowDataPollTrace;
                        switch (i) {
                            case 0:
                                MotionToolsResponse.Builder builder = builderNewBuilder3;
                                DdmHandleMotionTool ddmHandleMotionTool = this;
                                BeginTraceRequest beginTraceRequest = (BeginTraceRequest) beginTrace;
                                DdmHandleMotionTool.Companion companion = DdmHandleMotionTool.Companion;
                                BeginTraceResponse.Builder builderNewBuilder4 = BeginTraceResponse.newBuilder();
                                MotionToolManager motionToolManager2 = ddmHandleMotionTool.motionToolManager;
                                String rootWindow = beginTraceRequest.getWindow().getRootWindow();
                                synchronized (motionToolManager2) {
                                    i2 = motionToolManager2.traceIdCounter + 1;
                                    motionToolManager2.traceIdCounter = i2;
                                    Log.d("MotionToolManager", "Begin Trace for id: " + i2);
                                    View rootView = motionToolManager2.windowManagerGlobal.getRootView(rootWindow);
                                    if (rootView == null) {
                                        throw new WindowNotFoundException(rootWindow);
                                    }
                                    motionToolManager2.traces.put(Integer.valueOf(i2), new TraceMetadata(rootWindow, 0L, new MotionToolManager$beginTrace$1(motionToolManager2.viewCapture.startCapture(rootView, rootWindow))));
                                }
                                builderNewBuilder4.copyOnWrite();
                                BeginTraceResponse.access$100((BeginTraceResponse) builderNewBuilder4.instance, i2);
                                builder.copyOnWrite();
                                MotionToolsResponse.access$800((MotionToolsResponse) builder.instance, (BeginTraceResponse) builderNewBuilder4.build());
                                return Unit.INSTANCE;
                            case 1:
                                MotionToolsResponse.Builder builder2 = builderNewBuilder3;
                                DdmHandleMotionTool ddmHandleMotionTool2 = this;
                                EndTraceRequest endTraceRequest = (EndTraceRequest) beginTrace;
                                DdmHandleMotionTool.Companion companion2 = DdmHandleMotionTool.Companion;
                                EndTraceResponse.Builder builderNewBuilder5 = EndTraceResponse.newBuilder();
                                MotionToolManager motionToolManager3 = ddmHandleMotionTool2.motionToolManager;
                                int traceId = endTraceRequest.getTraceId();
                                synchronized (motionToolManager3) {
                                    Log.d("MotionToolManager", "End Trace for id: " + traceId);
                                    Object obj = ((LinkedHashMap) motionToolManager3.traces).get(Integer.valueOf(traceId));
                                    if (obj == null) {
                                        throw new UnknownTraceIdException(traceId);
                                    }
                                    motionWindowDataPollTrace = motionToolManager3.pollTrace(traceId);
                                    ((TraceMetadata) obj).stopTrace.invoke();
                                    motionToolManager3.traces.remove(Integer.valueOf(traceId));
                                }
                                builderNewBuilder5.copyOnWrite();
                                EndTraceResponse.access$100((EndTraceResponse) builderNewBuilder5.instance, motionWindowDataPollTrace);
                                builder2.copyOnWrite();
                                MotionToolsResponse.access$1100((MotionToolsResponse) builder2.instance, (EndTraceResponse) builderNewBuilder5.build());
                                return Unit.INSTANCE;
                            default:
                                DdmHandleMotionTool.Companion companion3 = DdmHandleMotionTool.Companion;
                                PollTraceResponse.Builder builderNewBuilder6 = PollTraceResponse.newBuilder();
                                MotionWindowData motionWindowDataPollTrace2 = this.motionToolManager.pollTrace(((PollTraceRequest) beginTrace).getTraceId());
                                builderNewBuilder6.copyOnWrite();
                                PollTraceResponse.access$100((PollTraceResponse) builderNewBuilder6.instance, motionWindowDataPollTrace2);
                                MotionToolsResponse.Builder builder3 = builderNewBuilder3;
                                builder3.copyOnWrite();
                                MotionToolsResponse.access$1400((MotionToolsResponse) builder3.instance, (PollTraceResponse) builderNewBuilder6.build());
                                return Unit.INSTANCE;
                        }
                    }
                });
                motionToolsResponse = (MotionToolsResponse) builderNewBuilder3.build();
            } else if (number == 3) {
                final EndTraceRequest endTrace = from.getEndTrace();
                final MotionToolsResponse.Builder builderNewBuilder4 = MotionToolsResponse.newBuilder();
                builderNewBuilder4.getClass();
                final int i2 = 1;
                tryCatchingMotionToolManagerExceptions(builderNewBuilder4, new Function0() { // from class: com.android.app.motiontool.DdmHandleMotionTool$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i22;
                        MotionWindowData motionWindowDataPollTrace;
                        switch (i2) {
                            case 0:
                                MotionToolsResponse.Builder builder = builderNewBuilder4;
                                DdmHandleMotionTool ddmHandleMotionTool = this;
                                BeginTraceRequest beginTraceRequest = (BeginTraceRequest) endTrace;
                                DdmHandleMotionTool.Companion companion = DdmHandleMotionTool.Companion;
                                BeginTraceResponse.Builder builderNewBuilder42 = BeginTraceResponse.newBuilder();
                                MotionToolManager motionToolManager2 = ddmHandleMotionTool.motionToolManager;
                                String rootWindow = beginTraceRequest.getWindow().getRootWindow();
                                synchronized (motionToolManager2) {
                                    i22 = motionToolManager2.traceIdCounter + 1;
                                    motionToolManager2.traceIdCounter = i22;
                                    Log.d("MotionToolManager", "Begin Trace for id: " + i22);
                                    View rootView = motionToolManager2.windowManagerGlobal.getRootView(rootWindow);
                                    if (rootView == null) {
                                        throw new WindowNotFoundException(rootWindow);
                                    }
                                    motionToolManager2.traces.put(Integer.valueOf(i22), new TraceMetadata(rootWindow, 0L, new MotionToolManager$beginTrace$1(motionToolManager2.viewCapture.startCapture(rootView, rootWindow))));
                                }
                                builderNewBuilder42.copyOnWrite();
                                BeginTraceResponse.access$100((BeginTraceResponse) builderNewBuilder42.instance, i22);
                                builder.copyOnWrite();
                                MotionToolsResponse.access$800((MotionToolsResponse) builder.instance, (BeginTraceResponse) builderNewBuilder42.build());
                                return Unit.INSTANCE;
                            case 1:
                                MotionToolsResponse.Builder builder2 = builderNewBuilder4;
                                DdmHandleMotionTool ddmHandleMotionTool2 = this;
                                EndTraceRequest endTraceRequest = (EndTraceRequest) endTrace;
                                DdmHandleMotionTool.Companion companion2 = DdmHandleMotionTool.Companion;
                                EndTraceResponse.Builder builderNewBuilder5 = EndTraceResponse.newBuilder();
                                MotionToolManager motionToolManager3 = ddmHandleMotionTool2.motionToolManager;
                                int traceId = endTraceRequest.getTraceId();
                                synchronized (motionToolManager3) {
                                    Log.d("MotionToolManager", "End Trace for id: " + traceId);
                                    Object obj = ((LinkedHashMap) motionToolManager3.traces).get(Integer.valueOf(traceId));
                                    if (obj == null) {
                                        throw new UnknownTraceIdException(traceId);
                                    }
                                    motionWindowDataPollTrace = motionToolManager3.pollTrace(traceId);
                                    ((TraceMetadata) obj).stopTrace.invoke();
                                    motionToolManager3.traces.remove(Integer.valueOf(traceId));
                                }
                                builderNewBuilder5.copyOnWrite();
                                EndTraceResponse.access$100((EndTraceResponse) builderNewBuilder5.instance, motionWindowDataPollTrace);
                                builder2.copyOnWrite();
                                MotionToolsResponse.access$1100((MotionToolsResponse) builder2.instance, (EndTraceResponse) builderNewBuilder5.build());
                                return Unit.INSTANCE;
                            default:
                                DdmHandleMotionTool.Companion companion3 = DdmHandleMotionTool.Companion;
                                PollTraceResponse.Builder builderNewBuilder6 = PollTraceResponse.newBuilder();
                                MotionWindowData motionWindowDataPollTrace2 = this.motionToolManager.pollTrace(((PollTraceRequest) endTrace).getTraceId());
                                builderNewBuilder6.copyOnWrite();
                                PollTraceResponse.access$100((PollTraceResponse) builderNewBuilder6.instance, motionWindowDataPollTrace2);
                                MotionToolsResponse.Builder builder3 = builderNewBuilder4;
                                builder3.copyOnWrite();
                                MotionToolsResponse.access$1400((MotionToolsResponse) builder3.instance, (PollTraceResponse) builderNewBuilder6.build());
                                return Unit.INSTANCE;
                        }
                    }
                });
                motionToolsResponse = (MotionToolsResponse) builderNewBuilder4.build();
            } else if (number != 4) {
                MotionToolsResponse.Builder builderNewBuilder5 = MotionToolsResponse.newBuilder();
                ErrorResponse.Builder builderNewBuilder6 = ErrorResponse.newBuilder();
                ErrorResponse.Code code = ErrorResponse.Code.INVALID_REQUEST;
                builderNewBuilder6.copyOnWrite();
                ErrorResponse.access$100((ErrorResponse) builderNewBuilder6.instance, code);
                builderNewBuilder6.copyOnWrite();
                ErrorResponse.access$300((ErrorResponse) builderNewBuilder6.instance, "Unknown request type");
                builderNewBuilder5.setError(builderNewBuilder6);
                motionToolsResponse = (MotionToolsResponse) builderNewBuilder5.build();
            } else {
                final PollTraceRequest pollTrace = from.getPollTrace();
                final MotionToolsResponse.Builder builderNewBuilder7 = MotionToolsResponse.newBuilder();
                builderNewBuilder7.getClass();
                final int i3 = 2;
                tryCatchingMotionToolManagerExceptions(builderNewBuilder7, new Function0() { // from class: com.android.app.motiontool.DdmHandleMotionTool$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i22;
                        MotionWindowData motionWindowDataPollTrace;
                        switch (i3) {
                            case 0:
                                MotionToolsResponse.Builder builder = builderNewBuilder7;
                                DdmHandleMotionTool ddmHandleMotionTool = this;
                                BeginTraceRequest beginTraceRequest = (BeginTraceRequest) pollTrace;
                                DdmHandleMotionTool.Companion companion = DdmHandleMotionTool.Companion;
                                BeginTraceResponse.Builder builderNewBuilder42 = BeginTraceResponse.newBuilder();
                                MotionToolManager motionToolManager2 = ddmHandleMotionTool.motionToolManager;
                                String rootWindow = beginTraceRequest.getWindow().getRootWindow();
                                synchronized (motionToolManager2) {
                                    i22 = motionToolManager2.traceIdCounter + 1;
                                    motionToolManager2.traceIdCounter = i22;
                                    Log.d("MotionToolManager", "Begin Trace for id: " + i22);
                                    View rootView = motionToolManager2.windowManagerGlobal.getRootView(rootWindow);
                                    if (rootView == null) {
                                        throw new WindowNotFoundException(rootWindow);
                                    }
                                    motionToolManager2.traces.put(Integer.valueOf(i22), new TraceMetadata(rootWindow, 0L, new MotionToolManager$beginTrace$1(motionToolManager2.viewCapture.startCapture(rootView, rootWindow))));
                                }
                                builderNewBuilder42.copyOnWrite();
                                BeginTraceResponse.access$100((BeginTraceResponse) builderNewBuilder42.instance, i22);
                                builder.copyOnWrite();
                                MotionToolsResponse.access$800((MotionToolsResponse) builder.instance, (BeginTraceResponse) builderNewBuilder42.build());
                                return Unit.INSTANCE;
                            case 1:
                                MotionToolsResponse.Builder builder2 = builderNewBuilder7;
                                DdmHandleMotionTool ddmHandleMotionTool2 = this;
                                EndTraceRequest endTraceRequest = (EndTraceRequest) pollTrace;
                                DdmHandleMotionTool.Companion companion2 = DdmHandleMotionTool.Companion;
                                EndTraceResponse.Builder builderNewBuilder52 = EndTraceResponse.newBuilder();
                                MotionToolManager motionToolManager3 = ddmHandleMotionTool2.motionToolManager;
                                int traceId = endTraceRequest.getTraceId();
                                synchronized (motionToolManager3) {
                                    Log.d("MotionToolManager", "End Trace for id: " + traceId);
                                    Object obj = ((LinkedHashMap) motionToolManager3.traces).get(Integer.valueOf(traceId));
                                    if (obj == null) {
                                        throw new UnknownTraceIdException(traceId);
                                    }
                                    motionWindowDataPollTrace = motionToolManager3.pollTrace(traceId);
                                    ((TraceMetadata) obj).stopTrace.invoke();
                                    motionToolManager3.traces.remove(Integer.valueOf(traceId));
                                }
                                builderNewBuilder52.copyOnWrite();
                                EndTraceResponse.access$100((EndTraceResponse) builderNewBuilder52.instance, motionWindowDataPollTrace);
                                builder2.copyOnWrite();
                                MotionToolsResponse.access$1100((MotionToolsResponse) builder2.instance, (EndTraceResponse) builderNewBuilder52.build());
                                return Unit.INSTANCE;
                            default:
                                DdmHandleMotionTool.Companion companion3 = DdmHandleMotionTool.Companion;
                                PollTraceResponse.Builder builderNewBuilder62 = PollTraceResponse.newBuilder();
                                MotionWindowData motionWindowDataPollTrace2 = this.motionToolManager.pollTrace(((PollTraceRequest) pollTrace).getTraceId());
                                builderNewBuilder62.copyOnWrite();
                                PollTraceResponse.access$100((PollTraceResponse) builderNewBuilder62.instance, motionWindowDataPollTrace2);
                                MotionToolsResponse.Builder builder3 = builderNewBuilder7;
                                builder3.copyOnWrite();
                                MotionToolsResponse.access$1400((MotionToolsResponse) builder3.instance, (PollTraceResponse) builderNewBuilder62.build());
                                return Unit.INSTANCE;
                        }
                    }
                });
                motionToolsResponse = (MotionToolsResponse) builderNewBuilder7.build();
            }
            byte[] byteArray = motionToolsResponse.toByteArray();
            return new Chunk(CHUNK_MOTO, byteArray, 0, byteArray.length);
        } catch (InvalidProtocolBufferException unused) {
            MotionToolsResponse.Builder builderNewBuilder8 = MotionToolsResponse.newBuilder();
            ErrorResponse.Builder builderNewBuilder9 = ErrorResponse.newBuilder();
            ErrorResponse.Code code2 = ErrorResponse.Code.INVALID_REQUEST;
            builderNewBuilder9.copyOnWrite();
            ErrorResponse.access$100((ErrorResponse) builderNewBuilder9.instance, code2);
            builderNewBuilder9.copyOnWrite();
            ErrorResponse.access$300((ErrorResponse) builderNewBuilder9.instance, "Invalid request format (Protobuf parse exception)");
            builderNewBuilder8.setError(builderNewBuilder9);
            byte[] byteArray2 = ((MotionToolsResponse) builderNewBuilder8.build()).toByteArray();
            return new Chunk(CHUNK_MOTO, byteArray2, 0, byteArray2.length);
        }
    }

    private DdmHandleMotionTool(MotionToolManager motionToolManager) {
        this.motionToolManager = motionToolManager;
    }

    public final void onConnected() {
    }

    public final void onDisconnected() {
    }
}
