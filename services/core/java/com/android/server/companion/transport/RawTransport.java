package com.android.server.companion.transport;

import android.companion.AssociationInfo;
import android.util.Slog;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.WindowMagnificationGestureHandler$$ExternalSyntheticOutline0;
import java.io.IOException;
import java.nio.ByteBuffer;
import libcore.io.IoUtils;
import libcore.io.Streams;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class RawTransport extends Transport {
    public volatile boolean mStopped;

    /* renamed from: $r8$lambda$kOck5G0Ky7-AcKXjGkOrtQvK6ss, reason: not valid java name */
    public static void m349$r8$lambda$kOck5G0Ky7AcKXjGkOrtQvK6ss(RawTransport rawTransport) {
        rawTransport.getClass();
        while (!rawTransport.mStopped) {
            try {
                rawTransport.receiveMessage();
            } catch (IOException e) {
                if (rawTransport.mStopped) {
                    return;
                }
                Slog.w("CDM_CompanionTransport", "Trouble during transport", e);
                rawTransport.stop();
                if (Transport.DEBUG) {
                    Slog.d("CDM_CompanionTransport", "Closing raw transport.");
                }
                IoUtils.closeQuietly(rawTransport.mRemoteIn);
                IoUtils.closeQuietly(rawTransport.mRemoteOut);
                CompanionTransportManager$$ExternalSyntheticLambda2 companionTransportManager$$ExternalSyntheticLambda2 = rawTransport.mOnTransportClosed;
                if (companionTransportManager$$ExternalSyntheticLambda2 != null) {
                    CompanionTransportManager companionTransportManager = companionTransportManager$$ExternalSyntheticLambda2.f$0;
                    companionTransportManager.getClass();
                    AssociationInfo associationById = companionTransportManager.mAssociationStore.getAssociationById(rawTransport.mAssociationId);
                    if (associationById != null) {
                        companionTransportManager.detachSystemDataTransport(associationById.getId());
                        return;
                    }
                    return;
                }
                return;
            }
        }
    }

    public final void receiveMessage() {
        synchronized (this.mRemoteIn) {
            byte[] bArr = new byte[12];
            Streams.readFully(this.mRemoteIn, bArr);
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            int i = wrap.getInt();
            int i2 = wrap.getInt();
            byte[] bArr2 = new byte[wrap.getInt()];
            Streams.readFully(this.mRemoteIn, bArr2);
            handleMessage(i, i2, bArr2);
        }
    }

    @Override // com.android.server.companion.transport.Transport
    public void sendMessage(int i, int i2, byte[] bArr) {
        if (Transport.DEBUG) {
            StringBuilder sb = new StringBuilder("Sending message 0x");
            sb.append(Integer.toHexString(i));
            sb.append(" sequence ");
            sb.append(i2);
            sb.append(" length ");
            sb.append(bArr.length);
            sb.append(" to association ");
            VaultKeeperService$$ExternalSyntheticOutline0.m(sb, this.mAssociationId, "CDM_CompanionTransport");
        }
        synchronized (this.mRemoteOut) {
            this.mRemoteOut.write(ByteBuffer.allocate(12).putInt(i).putInt(i2).putInt(bArr.length).array());
            this.mRemoteOut.write(bArr);
            this.mRemoteOut.flush();
        }
    }

    @Override // com.android.server.companion.transport.Transport
    public final void start() {
        if (Transport.DEBUG) {
            Slog.d("CDM_CompanionTransport", "Starting raw transport.");
        }
        new Thread(new Runnable() { // from class: com.android.server.companion.transport.RawTransport$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RawTransport.m349$r8$lambda$kOck5G0Ky7AcKXjGkOrtQvK6ss(RawTransport.this);
            }
        }).start();
    }

    @Override // com.android.server.companion.transport.Transport
    public final void stop() {
        if (Transport.DEBUG) {
            Slog.d("CDM_CompanionTransport", "Stopping raw transport.");
        }
        this.mStopped = true;
    }

    public final String toString() {
        return WindowMagnificationGestureHandler$$ExternalSyntheticOutline0.m(new StringBuilder("RawTransport{mAssociationId="), this.mAssociationId, '}');
    }
}
