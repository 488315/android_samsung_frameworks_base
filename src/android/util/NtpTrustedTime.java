package android.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.SntpClient;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.text.TextUtils;
import com.android.internal.R;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public abstract class NtpTrustedTime implements TrustedTime {
    private static final int FAIL_VISITED = 1;
    private static final boolean LOGD = true;
    public static final String NTP_SETTING_SERVER_NAME_DELIMITER = "|";
    private static final String NTP_SETTING_SERVER_NAME_DELIMITER_REGEXP = "\\|";
    private static final int SUC_VISITED = 2;
    private static final String TAG = "NtpTrustedTime";
    private static final String URI_SCHEME_NTP = "ntp";
    private static NtpTrustedTime sSingleton;
    private volatile URI mLastSuccessfulNtpServerUri;
    private NtpConfig mNtpConfigForTests;
    private volatile URI mOtherLastSuccessfulNtpServerUri;
    private volatile TimeResult mTimeResult;
    private final Object mRefreshLock = new Object();
    private final Object mConfigLock = new Object();
    private final SparseIntArray visitedUri = new SparseIntArray();

    static /* synthetic */ Integer lambda$forceRefreshCN$2(Integer num, Integer num2) {
        return num;
    }

    public abstract Network getDefaultNetwork();

    public abstract NtpConfig getNtpConfigInternal();

    public abstract boolean isNetworkConnected(Network network);

    public abstract TimeResult queryNtpServer(Network network, URI uri, java.time.Duration duration);

    public static final class NtpConfig {
        private final List<URI> mServerUris;
        private final java.time.Duration mTimeout;

        public NtpConfig(List<URI> list, java.time.Duration duration) throws IllegalArgumentException {
            Objects.requireNonNull(list);
            if (list.isEmpty()) {
                throw new IllegalArgumentException("Server URIs is empty");
            }
            ArrayList arrayList = new ArrayList();
            Iterator<URI> it = list.iterator();
            while (it.hasNext()) {
                try {
                    arrayList.add(NtpTrustedTime.validateNtpServerUri((URI) Objects.requireNonNull(it.next())));
                } catch (URISyntaxException e) {
                    throw new IllegalArgumentException("Bad server URI", e);
                }
            }
            this.mServerUris = Collections.unmodifiableList(arrayList);
            if (duration.isNegative() || duration.isZero()) {
                throw new IllegalArgumentException("timeout < 0");
            }
            this.mTimeout = duration;
        }

        public List<URI> getServerUris() {
            return this.mServerUris;
        }

        public java.time.Duration getTimeout() {
            return this.mTimeout;
        }

        public String toString() {
            return "NtpConnectionInfo{mServerUris=" + this.mServerUris + ", mTimeout=" + this.mTimeout + '}';
        }
    }

    public static class TimeResult {
        private final long mElapsedRealtimeMillis;
        private final InetSocketAddress mNtpServerSocketAddress;
        private final int mUncertaintyMillis;
        private final long mUnixEpochTimeMillis;

        public TimeResult(long j, long j2, int i, InetSocketAddress inetSocketAddress) {
            this.mUnixEpochTimeMillis = j;
            this.mElapsedRealtimeMillis = j2;
            this.mUncertaintyMillis = i;
            this.mNtpServerSocketAddress = (InetSocketAddress) Objects.requireNonNull(inetSocketAddress);
        }

        public long getTimeMillis() {
            return this.mUnixEpochTimeMillis;
        }

        public long getElapsedRealtimeMillis() {
            return this.mElapsedRealtimeMillis;
        }

        public int getUncertaintyMillis() {
            return this.mUncertaintyMillis;
        }

        public long currentTimeMillis() {
            return this.mUnixEpochTimeMillis + getAgeMillis();
        }

        public long getAgeMillis() {
            return getAgeMillis(SystemClock.elapsedRealtime());
        }

        public long getAgeMillis(long j) {
            return j - this.mElapsedRealtimeMillis;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof TimeResult)) {
                return false;
            }
            TimeResult timeResult = (TimeResult) obj;
            return this.mUnixEpochTimeMillis == timeResult.mUnixEpochTimeMillis && this.mElapsedRealtimeMillis == timeResult.mElapsedRealtimeMillis && this.mUncertaintyMillis == timeResult.mUncertaintyMillis && this.mNtpServerSocketAddress.equals(timeResult.mNtpServerSocketAddress);
        }

        public int hashCode() {
            return Objects.hash(Long.valueOf(this.mUnixEpochTimeMillis), Long.valueOf(this.mElapsedRealtimeMillis), Integer.valueOf(this.mUncertaintyMillis), this.mNtpServerSocketAddress);
        }

        public String toString() {
            return "TimeResult{unixEpochTime=" + Instant.ofEpochMilli(this.mUnixEpochTimeMillis) + ", elapsedRealtime=" + java.time.Duration.ofMillis(this.mElapsedRealtimeMillis) + ", mUncertaintyMillis=" + this.mUncertaintyMillis + ", mNtpServerSocketAddress=" + this.mNtpServerSocketAddress + '}';
        }
    }

    protected NtpTrustedTime() {
    }

    public static synchronized NtpTrustedTime getInstance(Context context) {
        NtpTrustedTime ntpTrustedTime;
        synchronized (NtpTrustedTime.class) {
            if (sSingleton == null) {
                sSingleton = new NtpTrustedTimeImpl(context.getApplicationContext());
            }
            ntpTrustedTime = sSingleton;
        }
        return ntpTrustedTime;
    }

    public void setServerConfigForTests(NtpConfig ntpConfig) {
        synchronized (this.mConfigLock) {
            this.mNtpConfigForTests = ntpConfig;
        }
    }

    @Override // android.util.TrustedTime
    public boolean forceRefresh() {
        synchronized (this.mRefreshLock) {
            Network defaultNetwork = getDefaultNetwork();
            if (defaultNetwork == null) {
                Log.d(TAG, "forceRefresh: no network available");
                return false;
            }
            return forceRefreshLocked(defaultNetwork);
        }
    }

    public boolean forceRefresh(Network network) {
        boolean forceRefreshLocked;
        Objects.requireNonNull(network);
        synchronized (this.mRefreshLock) {
            forceRefreshLocked = forceRefreshLocked(network);
        }
        return forceRefreshLocked;
    }

    private boolean forceRefreshLocked(Network network) {
        Objects.requireNonNull(network);
        if (!isNetworkConnected(network)) {
            Log.d(TAG, "forceRefreshLocked: network=" + network + " is not connected");
            return false;
        }
        String str = SystemProperties.get("persist.ril.ntptrustedtime");
        if ("off".equals(str)) {
            Log.d(TAG, "forceRefresh: persist.ril.ntptrustedtime (" + str + NavigationBarInflaterView.KEY_CODE_END);
            return false;
        }
        NtpConfig ntpConfig = getNtpConfig();
        if (ntpConfig == null) {
            Log.d(TAG, "forceRefreshLocked: invalid server config");
            return false;
        }
        Log.d(TAG, "forceRefreshLocked: NTP request network=" + network + " ntpConfig=" + ntpConfig);
        List<URI> serverUris = ntpConfig.getServerUris();
        String str2 = SystemProperties.get("ro.csc.countryiso_code", "");
        ArrayList arrayList = new ArrayList();
        for (URI uri : serverUris) {
            if (uri.equals(this.mLastSuccessfulNtpServerUri)) {
                arrayList.add(0, uri);
            } else if ("CN".equals(str2) && arrayList.size() > 0 && uri.equals(this.mOtherLastSuccessfulNtpServerUri)) {
                arrayList.add(1, uri);
            } else {
                arrayList.add(uri);
            }
        }
        if ("CN".equals(str2)) {
            return forceRefreshCN(network, ntpConfig, arrayList);
        }
        for (URI uri2 : arrayList) {
            TimeResult queryNtpServer = queryNtpServer(network, uri2, ntpConfig.getTimeout());
            if (queryNtpServer != null) {
                this.mLastSuccessfulNtpServerUri = uri2;
                this.mTimeResult = queryNtpServer;
                return true;
            }
        }
        return false;
    }

    private NtpConfig getNtpConfig() {
        synchronized (this.mConfigLock) {
            NtpConfig ntpConfig = this.mNtpConfigForTests;
            if (ntpConfig != null) {
                return ntpConfig;
            }
            return getNtpConfigInternal();
        }
    }

    @Override // android.util.TrustedTime
    @Deprecated
    public boolean hasCache() {
        return this.mTimeResult != null;
    }

    @Override // android.util.TrustedTime
    @Deprecated
    public long getCacheAge() {
        TimeResult timeResult = this.mTimeResult;
        if (timeResult != null) {
            return SystemClock.elapsedRealtime() - timeResult.getElapsedRealtimeMillis();
        }
        return Long.MAX_VALUE;
    }

    @Override // android.util.TrustedTime
    @Deprecated
    public long currentTimeMillis() {
        TimeResult timeResult = this.mTimeResult;
        if (timeResult == null) {
            throw new IllegalStateException("Missing authoritative time source");
        }
        Log.d(TAG, "currentTimeMillis() cache hit");
        return timeResult.currentTimeMillis();
    }

    @Deprecated
    public long getCachedNtpTime() {
        Log.d(TAG, "getCachedNtpTime() cache hit");
        TimeResult timeResult = this.mTimeResult;
        if (timeResult == null) {
            return 0L;
        }
        return timeResult.getTimeMillis();
    }

    @Deprecated
    public long getCachedNtpTimeReference() {
        TimeResult timeResult = this.mTimeResult;
        if (timeResult == null) {
            return 0L;
        }
        return timeResult.getElapsedRealtimeMillis();
    }

    public TimeResult getCachedTimeResult() {
        return this.mTimeResult;
    }

    public void setCachedTimeResult(TimeResult timeResult) {
        synchronized (this.mRefreshLock) {
            this.mTimeResult = timeResult;
        }
    }

    public void clearCachedTimeResult() {
        synchronized (this.mRefreshLock) {
            this.mTimeResult = null;
        }
    }

    public static URI parseNtpUriStrict(String str) throws URISyntaxException {
        return validateNtpServerUri(new URI(str));
    }

    public static List<URI> parseNtpServerSetting(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String[] split = str.split(NTP_SETTING_SERVER_NAME_DELIMITER_REGEXP);
        if (split.length == 0) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (String str2 : split) {
            if (str2.startsWith("ntp:")) {
                try {
                    arrayList.add(parseNtpUriStrict(str2));
                } catch (URISyntaxException e) {
                    Log.w(TAG, "Rejected NTP uri setting=" + str, e);
                    return null;
                }
            } else {
                try {
                    arrayList.add(validateNtpServerUri(new URI(URI_SCHEME_NTP, str2, null, null)));
                } catch (URISyntaxException e2) {
                    Log.w(TAG, "Rejected NTP legacy setting=" + str, e2);
                    return null;
                }
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static URI validateNtpServerUri(URI uri) throws URISyntaxException {
        if (!uri.isAbsolute()) {
            throw new URISyntaxException(uri.toString(), "Relative URI not supported");
        }
        if (!URI_SCHEME_NTP.equals(uri.getScheme())) {
            throw new URISyntaxException(uri.toString(), "Unrecognized scheme");
        }
        if (TextUtils.isEmpty(uri.getHost())) {
            throw new URISyntaxException(uri.toString(), "Missing host");
        }
        return uri;
    }

    public void dump(PrintWriter printWriter) {
        synchronized (this.mConfigLock) {
            printWriter.println("getNtpConfig()=" + getNtpConfig());
            printWriter.println("mNtpConfigForTests=" + this.mNtpConfigForTests);
        }
        printWriter.println("mLastSuccessfulNtpServerUri=" + this.mLastSuccessfulNtpServerUri);
        printWriter.println("mOtherLastSuccessfulNtpServerUri=" + this.mOtherLastSuccessfulNtpServerUri);
        TimeResult timeResult = this.mTimeResult;
        printWriter.println("mTimeResult=" + timeResult);
        if (timeResult != null) {
            printWriter.println("mTimeResult.getAgeMillis()=" + java.time.Duration.ofMillis(timeResult.getAgeMillis()));
        }
    }

    private static final class NtpTrustedTimeImpl extends NtpTrustedTime {
        private ConnectivityManager mConnectivityManager;
        private final Context mContext;

        private static int saturatedCast(long j) {
            if (j > 2147483647L) {
                return Integer.MAX_VALUE;
            }
            if (j < -2147483648L) {
                return Integer.MIN_VALUE;
            }
            return (int) j;
        }

        private NtpTrustedTimeImpl(Context context) {
            this.mContext = (Context) Objects.requireNonNull(context);
        }

        @Override // android.util.NtpTrustedTime
        public NtpConfig getNtpConfigInternal() {
            String[] stringArray;
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Resources resources = this.mContext.getResources();
            List<URI> parseNtpServerSetting = parseNtpServerSetting(Settings.Global.getString(contentResolver, Settings.Global.NTP_SERVER));
            if (parseNtpServerSetting == null) {
                resources.getStringArray(R.array.config_ntpServers);
                String str = SystemProperties.get("ro.csc.countryiso_code", "");
                String str2 = SystemProperties.get("persist.sys.timezone");
                if ("CN".equals(str)) {
                    stringArray = resources.getStringArray(R.array.config_ntpServers_China);
                } else if ("HK".equals(str)) {
                    stringArray = resources.getStringArray(R.array.config_ntpServers_HongKong);
                } else if ("TW".equals(str)) {
                    stringArray = resources.getStringArray(R.array.config_ntpServers_TaiWan);
                } else if (str2.contains("Asia")) {
                    stringArray = resources.getStringArray(R.array.config_ntpServers_Asia);
                } else if (str2.contains("Europe")) {
                    stringArray = resources.getStringArray(R.array.config_ntpServers_Europe);
                } else if (str2.contains("America")) {
                    stringArray = resources.getStringArray(R.array.config_ntpServers_America);
                } else {
                    stringArray = resources.getStringArray(R.array.config_ntpServers_Etc);
                }
                try {
                    ArrayList arrayList = new ArrayList();
                    for (String str3 : stringArray) {
                        arrayList.add(parseNtpUriStrict(str3));
                    }
                    parseNtpServerSetting = arrayList;
                } catch (URISyntaxException unused) {
                    parseNtpServerSetting = null;
                }
            }
            java.time.Duration ofMillis = java.time.Duration.ofMillis(Settings.Global.getInt(contentResolver, Settings.Global.NTP_TIMEOUT, resources.getInteger(R.integer.config_ntpTimeout)));
            if (parseNtpServerSetting == null) {
                return null;
            }
            return new NtpConfig(parseNtpServerSetting, ofMillis);
        }

        @Override // android.util.NtpTrustedTime
        public Network getDefaultNetwork() {
            ConnectivityManager connectivityManager = getConnectivityManager();
            if (connectivityManager == null) {
                return null;
            }
            return connectivityManager.getActiveNetwork();
        }

        @Override // android.util.NtpTrustedTime
        public boolean isNetworkConnected(Network network) {
            ConnectivityManager connectivityManager = getConnectivityManager();
            if (connectivityManager == null) {
                return false;
            }
            NetworkInfo networkInfo = connectivityManager.getNetworkInfo(network);
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
            Log.d(NtpTrustedTime.TAG, "getNetwork: no connectivity");
            return false;
        }

        private synchronized ConnectivityManager getConnectivityManager() {
            if (this.mConnectivityManager == null) {
                this.mConnectivityManager = (ConnectivityManager) this.mContext.getSystemService(ConnectivityManager.class);
            }
            if (this.mConnectivityManager == null) {
                Log.d(NtpTrustedTime.TAG, "getConnectivityManager: no ConnectivityManager");
            }
            return this.mConnectivityManager;
        }

        @Override // android.util.NtpTrustedTime
        public TimeResult queryNtpServer(Network network, URI uri, java.time.Duration duration) {
            SntpClient sntpClient = new SntpClient();
            if (sntpClient.requestTime(uri.getHost(), uri.getPort() == -1 ? 123 : uri.getPort(), saturatedCast(duration.toMillis()), network)) {
                return new TimeResult(sntpClient.getNtpTime(), sntpClient.getNtpTimeReference(), saturatedCast(sntpClient.getRoundTripTime() / 2), sntpClient.getServerSocketAddress());
            }
            return null;
        }
    }

    private boolean forceRefreshCN(final Network network, final NtpConfig ntpConfig, final List<URI> list) {
        this.visitedUri.clear();
        this.mLastSuccessfulNtpServerUri = null;
        this.mOtherLastSuccessfulNtpServerUri = null;
        final CountDownLatch countDownLatch = new CountDownLatch(2);
        final TimeResult[] timeResultArr = {null, null};
        final URI[] uriArr = {null, null};
        for (final int i = 0; i < 2; i++) {
            new Thread(new Runnable() { // from class: android.util.NtpTrustedTime$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    NtpTrustedTime.this.lambda$forceRefreshCN$0(i, list, network, ntpConfig, timeResultArr, uriArr, countDownLatch);
                }
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Log.e(TAG, "[CN]forceRefreshLocked: wait thread result fail", e);
        }
        TimeResult timeResult = timeResultArr[0];
        if (timeResult == null || timeResultArr[1] == null) {
            Log.d(TAG, "[CN]forceRefreshLocked: back to original logic");
            if (timeResultArr[0] != null) {
                this.mLastSuccessfulNtpServerUri = uriArr[0];
                this.mTimeResult = timeResultArr[0];
                return true;
            }
            if (timeResultArr[1] == null) {
                return false;
            }
            this.mLastSuccessfulNtpServerUri = uriArr[1];
            this.mTimeResult = timeResultArr[1];
            return true;
        }
        long timeMillis = timeResult.getTimeMillis();
        long timeMillis2 = timeResultArr[1].getTimeMillis();
        long elapsedRealtimeMillis = timeResultArr[0].getElapsedRealtimeMillis();
        long elapsedRealtimeMillis2 = timeMillis2 - timeResultArr[1].getElapsedRealtimeMillis();
        if (Math.abs((timeMillis - elapsedRealtimeMillis) - elapsedRealtimeMillis2) > 60000) {
            Log.d(TAG, "[CN]forceRefreshLocked: need more ntp result to compare");
            Stream<Integer> filter = IntStream.range(0, list.size()).boxed().filter(new Predicate() { // from class: android.util.NtpTrustedTime$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$forceRefreshCN$1;
                    lambda$forceRefreshCN$1 = NtpTrustedTime.this.lambda$forceRefreshCN$1((Integer) obj);
                    return lambda$forceRefreshCN$1;
                }
            });
            Objects.requireNonNull(list);
            Function function = new Function() { // from class: android.util.NtpTrustedTime$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (URI) list.get(((Integer) obj).intValue());
                }
            };
            final SparseIntArray sparseIntArray = this.visitedUri;
            Objects.requireNonNull(sparseIntArray);
            List list2 = (List) ((LinkedHashMap) filter.collect(Collectors.toMap(function, new Function() { // from class: android.util.NtpTrustedTime$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(SparseIntArray.this.get(((Integer) obj).intValue()));
                }
            }, new BinaryOperator() { // from class: android.util.NtpTrustedTime$$ExternalSyntheticLambda4
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return NtpTrustedTime.lambda$forceRefreshCN$2((Integer) obj, (Integer) obj2);
                }
            }, new NtpTrustedTime$$ExternalSyntheticLambda5()))).entrySet().stream().sorted(Map.Entry.comparingByValue()).map(new Function() { // from class: android.util.NtpTrustedTime$$ExternalSyntheticLambda6
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return (URI) ((Map.Entry) obj).getKey();
                }
            }).collect(Collectors.toList());
            int i2 = 0;
            while (true) {
                if (i2 >= list2.size()) {
                    break;
                }
                TimeResult queryNtpServer = queryNtpServer(network, (URI) list2.get(i2), ntpConfig.getTimeout());
                if (queryNtpServer != null) {
                    Log.d(TAG, "[CN]forceRefreshLocked: get ntp result=" + queryNtpServer);
                    if (Math.abs(elapsedRealtimeMillis2 - (queryNtpServer.getTimeMillis() - queryNtpServer.getElapsedRealtimeMillis())) <= 60000) {
                        Log.d(TAG, "[CN]forceRefreshLocked: return the second success url");
                        this.mLastSuccessfulNtpServerUri = uriArr[1];
                        this.mOtherLastSuccessfulNtpServerUri = uriArr[0];
                        this.mTimeResult = timeResultArr[1];
                        return true;
                    }
                } else {
                    i2++;
                }
            }
        }
        Log.d(TAG, "[CN]forceRefreshLocked: return the first success url");
        this.mLastSuccessfulNtpServerUri = uriArr[0];
        this.mOtherLastSuccessfulNtpServerUri = uriArr[1];
        this.mTimeResult = timeResultArr[0];
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$forceRefreshCN$0(int i, List list, Network network, NtpConfig ntpConfig, TimeResult[] timeResultArr, URI[] uriArr, CountDownLatch countDownLatch) {
        int i2 = i;
        while (true) {
            if (i2 >= list.size()) {
                break;
            }
            TimeResult queryNtpServer = queryNtpServer(network, (URI) list.get(i2), ntpConfig.getTimeout());
            if (queryNtpServer != null) {
                Log.d(TAG, "[CN]forceRefreshLocked: [" + i + "] get ntp result=" + queryNtpServer);
                this.visitedUri.put(i2, 2);
                timeResultArr[i] = queryNtpServer;
                uriArr[i] = (URI) list.get(i2);
                break;
            }
            this.visitedUri.put(i2, 1);
            i2 += 2;
        }
        countDownLatch.countDown();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$forceRefreshCN$1(Integer num) {
        return this.visitedUri.get(num.intValue()) != 2;
    }
}
