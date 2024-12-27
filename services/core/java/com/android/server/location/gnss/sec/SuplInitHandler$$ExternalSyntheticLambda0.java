package com.android.server.location.gnss.sec;

import android.net.TrafficStats;
import android.net.util.NetworkConstants;
import android.util.Log;

import com.android.server.location.LocationServiceThread;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketTimeoutException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SuplInitHandler$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ SuplInitHandler f$0;

    public /* synthetic */ SuplInitHandler$$ExternalSyntheticLambda0(
            SuplInitHandler suplInitHandler) {
        this.f$0 = suplInitHandler;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SuplInitHandler suplInitHandler = this.f$0;
        suplInitHandler.getClass();
        TrafficStats.setThreadStatsTag((int) Thread.currentThread().getId());
        while (suplInitHandler.mIsUdpListen) {
            try {
                DatagramSocket datagramSocket = new DatagramSocket(7275);
                try {
                    suplInitHandler.mIsOpenUdpPort = true;
                    DatagramPacket datagramPacket =
                            new DatagramPacket(
                                    new byte[NetworkConstants.ETHER_MTU],
                                    NetworkConstants.ETHER_MTU);
                    datagramSocket.setSoTimeout(60000);
                    datagramSocket.receive(datagramPacket);
                    Log.d("SuplInitHandler", "received data through 7275 UDP port");
                    if (datagramPacket.getLength() > 0) {
                        LocationServiceThread.getHandler()
                                .post(
                                        new SuplInitHandler$$ExternalSyntheticLambda1(
                                                suplInitHandler, datagramPacket, 2));
                        suplInitHandler.mIsOpenUdpPort = false;
                    }
                    datagramSocket.close();
                } catch (Throwable th) {
                    try {
                        datagramSocket.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (SocketTimeoutException unused) {
                suplInitHandler.mIsOpenUdpPort = false;
            } catch (IOException e) {
                e.printStackTrace();
                suplInitHandler.mIsOpenUdpPort = false;
            }
        }
        TrafficStats.clearThreadStatsTag();
    }
}
