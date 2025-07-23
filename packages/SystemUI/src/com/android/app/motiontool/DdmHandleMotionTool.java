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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DdmHandleMotionTool extends DdmHandle {
    public static DdmHandleMotionTool INSTANCE;
    public final MotionToolManager motionToolManager;
    public static final Companion Companion = new Companion(null);
    public static final int CHUNK_MOTO = ChunkHandler.type("MOTO");

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            ErrorResponse.Builder newBuilder = ErrorResponse.newBuilder();
            ErrorResponse.Code code = ErrorResponse.Code.UNKNOWN_TRACE_ID;
            newBuilder.copyOnWrite();
            ErrorResponse.access$100((ErrorResponse) newBuilder.instance, code);
            newBuilder.copyOnWrite();
            ErrorResponse.access$300((ErrorResponse) newBuilder.instance, "No running Trace found with traceId " + traceId);
            builder.setError(newBuilder);
        } catch (WindowNotFoundException e2) {
            String windowId = e2.getWindowId();
            ErrorResponse.Builder newBuilder2 = ErrorResponse.newBuilder();
            ErrorResponse.Code code2 = ErrorResponse.Code.WINDOW_NOT_FOUND;
            newBuilder2.copyOnWrite();
            ErrorResponse.access$100((ErrorResponse) newBuilder2.instance, code2);
            newBuilder2.copyOnWrite();
            ErrorResponse.access$300((ErrorResponse) newBuilder2.instance, "No window found with windowId " + windowId);
            builder.setError(newBuilder2);
        }
    }

    public final Chunk handleChunk(Chunk chunk) {
        boolean z;
        MotionToolsResponse motionToolsResponse;
        try {
            MotionToolsRequest parseFrom = MotionToolsRequest.parseFrom(DdmHandle.wrapChunk(chunk).array());
            int number = parseFrom.getTypeCase().getNumber();
            if (number == 1) {
                HandshakeRequest handshake = parseFrom.getHandshake();
                MotionToolManager motionToolManager = this.motionToolManager;
                WindowIdentifier window = handshake.getWindow();
                synchronized (motionToolManager) {
                    z = motionToolManager.windowManagerGlobal.getRootView(window.getRootWindow()) != null;
                }
                HandshakeResponse.Status status = z ? HandshakeResponse.Status.OK : HandshakeResponse.Status.WINDOW_NOT_FOUND;
                MotionToolsResponse.Builder newBuilder = MotionToolsResponse.newBuilder();
                HandshakeResponse.Builder newBuilder2 = HandshakeResponse.newBuilder();
                newBuilder2.copyOnWrite();
                HandshakeResponse.access$300((HandshakeResponse) newBuilder2.instance);
                newBuilder2.copyOnWrite();
                HandshakeResponse.access$100((HandshakeResponse) newBuilder2.instance, status);
                newBuilder.copyOnWrite();
                MotionToolsResponse.access$500((MotionToolsResponse) newBuilder.instance, (HandshakeResponse) newBuilder2.build());
                motionToolsResponse = (MotionToolsResponse) newBuilder.build();
            } else if (number == 2) {
                final BeginTraceRequest beginTrace = parseFrom.getBeginTrace();
                final MotionToolsResponse.Builder newBuilder3 = MotionToolsResponse.newBuilder();
                newBuilder3.getClass();
                final int i = 0;
                tryCatchingMotionToolManagerExceptions(newBuilder3, new Function0() { // from class: com.android.app.motiontool.DdmHandleMotionTool$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i2;
                        MotionWindowData pollTrace;
                        switch (i) {
                            case 0:
                                MotionToolsResponse.Builder builder = newBuilder3;
                                DdmHandleMotionTool ddmHandleMotionTool = this;
                                BeginTraceRequest beginTraceRequest = (BeginTraceRequest) beginTrace;
                                DdmHandleMotionTool.Companion companion = DdmHandleMotionTool.Companion;
                                BeginTraceResponse.Builder newBuilder4 = BeginTraceResponse.newBuilder();
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
                                newBuilder4.copyOnWrite();
                                BeginTraceResponse.access$100((BeginTraceResponse) newBuilder4.instance, i2);
                                builder.copyOnWrite();
                                MotionToolsResponse.access$800((MotionToolsResponse) builder.instance, (BeginTraceResponse) newBuilder4.build());
                                return Unit.INSTANCE;
                            case 1:
                                MotionToolsResponse.Builder builder2 = newBuilder3;
                                DdmHandleMotionTool ddmHandleMotionTool2 = this;
                                EndTraceRequest endTraceRequest = (EndTraceRequest) beginTrace;
                                DdmHandleMotionTool.Companion companion2 = DdmHandleMotionTool.Companion;
                                EndTraceResponse.Builder newBuilder5 = EndTraceResponse.newBuilder();
                                MotionToolManager motionToolManager3 = ddmHandleMotionTool2.motionToolManager;
                                int traceId = endTraceRequest.getTraceId();
                                synchronized (motionToolManager3) {
                                    Log.d("MotionToolManager", "End Trace for id: " + traceId);
                                    Object obj = ((LinkedHashMap) motionToolManager3.traces).get(Integer.valueOf(traceId));
                                    if (obj == null) {
                                        throw new UnknownTraceIdException(traceId);
                                    }
                                    pollTrace = motionToolManager3.pollTrace(traceId);
                                    ((TraceMetadata) obj).stopTrace.invoke();
                                    motionToolManager3.traces.remove(Integer.valueOf(traceId));
                                }
                                newBuilder5.copyOnWrite();
                                EndTraceResponse.access$100((EndTraceResponse) newBuilder5.instance, pollTrace);
                                builder2.copyOnWrite();
                                MotionToolsResponse.access$1100((MotionToolsResponse) builder2.instance, (EndTraceResponse) newBuilder5.build());
                                return Unit.INSTANCE;
                            default:
                                DdmHandleMotionTool.Companion companion3 = DdmHandleMotionTool.Companion;
                                PollTraceResponse.Builder newBuilder6 = PollTraceResponse.newBuilder();
                                MotionWindowData pollTrace2 = this.motionToolManager.pollTrace(((PollTraceRequest) beginTrace).getTraceId());
                                newBuilder6.copyOnWrite();
                                PollTraceResponse.access$100((PollTraceResponse) newBuilder6.instance, pollTrace2);
                                MotionToolsResponse.Builder builder3 = newBuilder3;
                                builder3.copyOnWrite();
                                MotionToolsResponse.access$1400((MotionToolsResponse) builder3.instance, (PollTraceResponse) newBuilder6.build());
                                return Unit.INSTANCE;
                        }
                    }
                });
                motionToolsResponse = (MotionToolsResponse) newBuilder3.build();
            } else if (number == 3) {
                final EndTraceRequest endTrace = parseFrom.getEndTrace();
                final MotionToolsResponse.Builder newBuilder4 = MotionToolsResponse.newBuilder();
                newBuilder4.getClass();
                final int i2 = 1;
                tryCatchingMotionToolManagerExceptions(newBuilder4, new Function0() { // from class: com.android.app.motiontool.DdmHandleMotionTool$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i22;
                        MotionWindowData pollTrace;
                        switch (i2) {
                            case 0:
                                MotionToolsResponse.Builder builder = newBuilder4;
                                DdmHandleMotionTool ddmHandleMotionTool = this;
                                BeginTraceRequest beginTraceRequest = (BeginTraceRequest) endTrace;
                                DdmHandleMotionTool.Companion companion = DdmHandleMotionTool.Companion;
                                BeginTraceResponse.Builder newBuilder42 = BeginTraceResponse.newBuilder();
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
                                newBuilder42.copyOnWrite();
                                BeginTraceResponse.access$100((BeginTraceResponse) newBuilder42.instance, i22);
                                builder.copyOnWrite();
                                MotionToolsResponse.access$800((MotionToolsResponse) builder.instance, (BeginTraceResponse) newBuilder42.build());
                                return Unit.INSTANCE;
                            case 1:
                                MotionToolsResponse.Builder builder2 = newBuilder4;
                                DdmHandleMotionTool ddmHandleMotionTool2 = this;
                                EndTraceRequest endTraceRequest = (EndTraceRequest) endTrace;
                                DdmHandleMotionTool.Companion companion2 = DdmHandleMotionTool.Companion;
                                EndTraceResponse.Builder newBuilder5 = EndTraceResponse.newBuilder();
                                MotionToolManager motionToolManager3 = ddmHandleMotionTool2.motionToolManager;
                                int traceId = endTraceRequest.getTraceId();
                                synchronized (motionToolManager3) {
                                    Log.d("MotionToolManager", "End Trace for id: " + traceId);
                                    Object obj = ((LinkedHashMap) motionToolManager3.traces).get(Integer.valueOf(traceId));
                                    if (obj == null) {
                                        throw new UnknownTraceIdException(traceId);
                                    }
                                    pollTrace = motionToolManager3.pollTrace(traceId);
                                    ((TraceMetadata) obj).stopTrace.invoke();
                                    motionToolManager3.traces.remove(Integer.valueOf(traceId));
                                }
                                newBuilder5.copyOnWrite();
                                EndTraceResponse.access$100((EndTraceResponse) newBuilder5.instance, pollTrace);
                                builder2.copyOnWrite();
                                MotionToolsResponse.access$1100((MotionToolsResponse) builder2.instance, (EndTraceResponse) newBuilder5.build());
                                return Unit.INSTANCE;
                            default:
                                DdmHandleMotionTool.Companion companion3 = DdmHandleMotionTool.Companion;
                                PollTraceResponse.Builder newBuilder6 = PollTraceResponse.newBuilder();
                                MotionWindowData pollTrace2 = this.motionToolManager.pollTrace(((PollTraceRequest) endTrace).getTraceId());
                                newBuilder6.copyOnWrite();
                                PollTraceResponse.access$100((PollTraceResponse) newBuilder6.instance, pollTrace2);
                                MotionToolsResponse.Builder builder3 = newBuilder4;
                                builder3.copyOnWrite();
                                MotionToolsResponse.access$1400((MotionToolsResponse) builder3.instance, (PollTraceResponse) newBuilder6.build());
                                return Unit.INSTANCE;
                        }
                    }
                });
                motionToolsResponse = (MotionToolsResponse) newBuilder4.build();
            } else if (number != 4) {
                MotionToolsResponse.Builder newBuilder5 = MotionToolsResponse.newBuilder();
                ErrorResponse.Builder newBuilder6 = ErrorResponse.newBuilder();
                ErrorResponse.Code code = ErrorResponse.Code.INVALID_REQUEST;
                newBuilder6.copyOnWrite();
                ErrorResponse.access$100((ErrorResponse) newBuilder6.instance, code);
                newBuilder6.copyOnWrite();
                ErrorResponse.access$300((ErrorResponse) newBuilder6.instance, "Unknown request type");
                newBuilder5.setError(newBuilder6);
                motionToolsResponse = (MotionToolsResponse) newBuilder5.build();
            } else {
                final PollTraceRequest pollTrace = parseFrom.getPollTrace();
                final MotionToolsResponse.Builder newBuilder7 = MotionToolsResponse.newBuilder();
                newBuilder7.getClass();
                final int i3 = 2;
                tryCatchingMotionToolManagerExceptions(newBuilder7, new Function0() { // from class: com.android.app.motiontool.DdmHandleMotionTool$$ExternalSyntheticLambda2
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        int i22;
                        MotionWindowData pollTrace2;
                        switch (i3) {
                            case 0:
                                MotionToolsResponse.Builder builder = newBuilder7;
                                DdmHandleMotionTool ddmHandleMotionTool = this;
                                BeginTraceRequest beginTraceRequest = (BeginTraceRequest) pollTrace;
                                DdmHandleMotionTool.Companion companion = DdmHandleMotionTool.Companion;
                                BeginTraceResponse.Builder newBuilder42 = BeginTraceResponse.newBuilder();
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
                                newBuilder42.copyOnWrite();
                                BeginTraceResponse.access$100((BeginTraceResponse) newBuilder42.instance, i22);
                                builder.copyOnWrite();
                                MotionToolsResponse.access$800((MotionToolsResponse) builder.instance, (BeginTraceResponse) newBuilder42.build());
                                return Unit.INSTANCE;
                            case 1:
                                MotionToolsResponse.Builder builder2 = newBuilder7;
                                DdmHandleMotionTool ddmHandleMotionTool2 = this;
                                EndTraceRequest endTraceRequest = (EndTraceRequest) pollTrace;
                                DdmHandleMotionTool.Companion companion2 = DdmHandleMotionTool.Companion;
                                EndTraceResponse.Builder newBuilder52 = EndTraceResponse.newBuilder();
                                MotionToolManager motionToolManager3 = ddmHandleMotionTool2.motionToolManager;
                                int traceId = endTraceRequest.getTraceId();
                                synchronized (motionToolManager3) {
                                    Log.d("MotionToolManager", "End Trace for id: " + traceId);
                                    Object obj = ((LinkedHashMap) motionToolManager3.traces).get(Integer.valueOf(traceId));
                                    if (obj == null) {
                                        throw new UnknownTraceIdException(traceId);
                                    }
                                    pollTrace2 = motionToolManager3.pollTrace(traceId);
                                    ((TraceMetadata) obj).stopTrace.invoke();
                                    motionToolManager3.traces.remove(Integer.valueOf(traceId));
                                }
                                newBuilder52.copyOnWrite();
                                EndTraceResponse.access$100((EndTraceResponse) newBuilder52.instance, pollTrace2);
                                builder2.copyOnWrite();
                                MotionToolsResponse.access$1100((MotionToolsResponse) builder2.instance, (EndTraceResponse) newBuilder52.build());
                                return Unit.INSTANCE;
                            default:
                                DdmHandleMotionTool.Companion companion3 = DdmHandleMotionTool.Companion;
                                PollTraceResponse.Builder newBuilder62 = PollTraceResponse.newBuilder();
                                MotionWindowData pollTrace22 = this.motionToolManager.pollTrace(((PollTraceRequest) pollTrace).getTraceId());
                                newBuilder62.copyOnWrite();
                                PollTraceResponse.access$100((PollTraceResponse) newBuilder62.instance, pollTrace22);
                                MotionToolsResponse.Builder builder3 = newBuilder7;
                                builder3.copyOnWrite();
                                MotionToolsResponse.access$1400((MotionToolsResponse) builder3.instance, (PollTraceResponse) newBuilder62.build());
                                return Unit.INSTANCE;
                        }
                    }
                });
                motionToolsResponse = (MotionToolsResponse) newBuilder7.build();
            }
            byte[] byteArray = motionToolsResponse.toByteArray();
            return new Chunk(CHUNK_MOTO, byteArray, 0, byteArray.length);
        } catch (InvalidProtocolBufferException unused) {
            MotionToolsResponse.Builder newBuilder8 = MotionToolsResponse.newBuilder();
            ErrorResponse.Builder newBuilder9 = ErrorResponse.newBuilder();
            ErrorResponse.Code code2 = ErrorResponse.Code.INVALID_REQUEST;
            newBuilder9.copyOnWrite();
            ErrorResponse.access$100((ErrorResponse) newBuilder9.instance, code2);
            newBuilder9.copyOnWrite();
            ErrorResponse.access$300((ErrorResponse) newBuilder9.instance, "Invalid request format (Protobuf parse exception)");
            newBuilder8.setError(newBuilder9);
            byte[] byteArray2 = ((MotionToolsResponse) newBuilder8.build()).toByteArray();
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
