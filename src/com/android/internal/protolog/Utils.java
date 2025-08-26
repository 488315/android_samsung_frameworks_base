package com.android.internal.protolog;

import android.internal.perfetto.protos.TracePacketOuterClass;
import android.os.SystemClock;
import android.tracing.perfetto.TraceFunction;
import android.tracing.perfetto.TracingContext;
import android.util.Log;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import java.io.IOException;

/* loaded from: classes4.dex */
public class Utils {
    private static final String LOG_TAG = "ProtoLogUtils";

    public static void dumpViewerConfig(ProtoLogDataSource protoLogDataSource, final ViewerConfigInputStreamProvider viewerConfigInputStreamProvider) {
        protoLogDataSource.trace(new TraceFunction() { // from class: com.android.internal.protolog.Utils$$ExternalSyntheticLambda0
            @Override // android.tracing.perfetto.TraceFunction
            public final void trace(TracingContext tracingContext) {
                Utils.lambda$dumpViewerConfig$0(viewerConfigInputStreamProvider, tracingContext);
            }
        });
    }

    static /* synthetic */ void lambda$dumpViewerConfig$0(ViewerConfigInputStreamProvider viewerConfigInputStreamProvider, TracingContext tracingContext) {
        try {
            AutoClosableProtoInputStream inputStream = viewerConfigInputStreamProvider.getInputStream();
            try {
                ProtoInputStream protoInputStream = inputStream.get();
                ProtoOutputStream protoOutputStreamNewTracePacket = tracingContext.newTracePacket();
                protoOutputStreamNewTracePacket.write(TracePacketOuterClass.TracePacket.TIMESTAMP, SystemClock.elapsedRealtimeNanos());
                long jStart = protoOutputStreamNewTracePacket.start(1146756268137L);
                while (protoInputStream.nextField() != -1) {
                    if (protoInputStream.getFieldNumber() == 1) {
                        writeViewerConfigMessage(protoInputStream, protoOutputStreamNewTracePacket);
                    }
                    if (protoInputStream.getFieldNumber() == 2) {
                        writeViewerConfigGroup(protoInputStream, protoOutputStreamNewTracePacket);
                    }
                }
                protoOutputStreamNewTracePacket.end(jStart);
                if (inputStream != null) {
                    inputStream.close();
                }
            } finally {
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Failed to read ProtoLog viewer config to dump to datasource", e);
        }
    }

    private static void writeViewerConfigGroup(ProtoInputStream protoInputStream, ProtoOutputStream protoOutputStream) throws IOException {
        long jStart = protoInputStream.start(2246267895810L);
        long jStart2 = protoOutputStream.start(2246267895810L);
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                protoOutputStream.write(1155346202625L, protoInputStream.readInt(1155346202625L));
            } else if (fieldNumber == 2) {
                protoOutputStream.write(1138166333442L, protoInputStream.readString(1138166333442L));
            } else if (fieldNumber == 3) {
                protoOutputStream.write(1138166333443L, protoInputStream.readString(1138166333443L));
            } else {
                Log.e(LOG_TAG, "Unexpected field id " + protoInputStream.getFieldNumber());
            }
        }
        protoInputStream.end(jStart);
        protoOutputStream.end(jStart2);
    }

    private static void writeViewerConfigMessage(ProtoInputStream protoInputStream, ProtoOutputStream protoOutputStream) throws IOException {
        long jStart = protoInputStream.start(2246267895809L);
        long jStart2 = protoOutputStream.start(2246267895809L);
        while (protoInputStream.nextField() != -1) {
            int fieldNumber = protoInputStream.getFieldNumber();
            if (fieldNumber == 1) {
                protoOutputStream.write(1125281431553L, protoInputStream.readLong(1125281431553L));
            } else if (fieldNumber == 2) {
                protoOutputStream.write(1138166333442L, protoInputStream.readString(1138166333442L));
            } else if (fieldNumber == 3) {
                protoOutputStream.write(1159641169923L, protoInputStream.readInt(1159641169923L));
            } else if (fieldNumber == 4) {
                protoOutputStream.write(1155346202628L, protoInputStream.readInt(1155346202628L));
            } else if (fieldNumber == 5) {
                protoOutputStream.write(1138166333445L, protoInputStream.readString(1138166333445L));
            } else {
                Log.e(LOG_TAG, "Unexpected field id " + protoInputStream.getFieldNumber());
            }
        }
        protoInputStream.end(jStart);
        protoOutputStream.end(jStart2);
    }
}
