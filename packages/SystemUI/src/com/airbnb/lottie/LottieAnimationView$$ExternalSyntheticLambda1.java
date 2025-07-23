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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class LottieAnimationView$$ExternalSyntheticLambda1 implements LottieListener {
    @Override // com.airbnb.lottie.LottieListener
    public final void onResult(Object obj) {
        Throwable th = (Throwable) obj;
        int i = LottieAnimationView.$r8$clinit;
        Utils.AnonymousClass1 anonymousClass1 = Utils.threadLocalPathMeasure;
        if (!(th instanceof SocketException) && !(th instanceof ClosedChannelException) && !(th instanceof InterruptedIOException) && !(th instanceof ProtocolException) && !(th instanceof SSLException) && !(th instanceof UnknownHostException) && !(th instanceof UnknownServiceException)) {
            throw new IllegalStateException("Unable to parse composition", th);
        }
        Logger.warning("Unable to load composition.", th);
    }
}
