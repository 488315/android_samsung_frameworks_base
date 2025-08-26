package com.airbnb.lottie.network;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/* loaded from: classes.dex */
public class DefaultLottieNetworkFetcher implements LottieNetworkFetcher {
    public final DefaultLottieFetchResult fetchSync(String str) throws ProtocolException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();
        return new DefaultLottieFetchResult(httpURLConnection);
    }
}
