package com.airbnb.lottie;

import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;
import java.nio.channels.ClosedChannelException;
import javax.net.ssl.SSLException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class LottieAnimationView$$ExternalSyntheticLambda1 implements LottieListener {
    @Override // com.airbnb.lottie.LottieListener
    public final void onResult(Object obj) {
        Throwable th = (Throwable) obj;
        LottieAnimationView$$ExternalSyntheticLambda1 lottieAnimationView$$ExternalSyntheticLambda1 = LottieAnimationView.DEFAULT_FAILURE_LISTENER;
        Utils.C06051 c06051 = Utils.threadLocalPathMeasure;
        if (!((th instanceof SocketException) || (th instanceof ClosedChannelException) || (th instanceof InterruptedIOException) || (th instanceof ProtocolException) || (th instanceof SSLException) || (th instanceof UnknownHostException) || (th instanceof UnknownServiceException))) {
            throw new IllegalStateException("Unable to parse composition", th);
        }
        Logger.warning("Unable to load composition.", th);
    }
}
