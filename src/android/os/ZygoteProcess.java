package android.os;

import android.content.pm.ASKSManager;
import android.content.pm.ApplicationInfo;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.os.Process;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteConfig;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/* loaded from: classes3.dex */
public class ZygoteProcess {
    private static final String[] INVALID_USAP_FLAGS = {"--query-abi-list", "--get-pid", "--preload-default", "--preload-package", "--preload-app", "--start-child-zygote", "--set-api-denylist-exemptions", "--hidden-api-log-sampling-rate", "--hidden-api-statslog-sampling-rate", "--invoke-with"};
    private static final String LOG_TAG = "ZygoteProcess";
    private static final int ZYGOTE_CONNECT_RETRY_DELAY_MS = 50;
    private static final int ZYGOTE_CONNECT_TIMEOUT_MS = 60000;
    static final int ZYGOTE_RETRY_MILLIS = 500;
    public static volatile int sAppZygotePreloadTimeoutMs = 15000;
    private List<String> mApiDenylistExemptions;
    private int mHiddenApiAccessLogSampleRate;
    private int mHiddenApiAccessStatslogSampleRate;
    private boolean mIsFirstPropCheck;
    private long mLastPropCheckTimestamp;
    private final Object mLock;
    private boolean mUsapPoolEnabled;
    private final LocalSocketAddress mUsapPoolSecondarySocketAddress;
    private final LocalSocketAddress mUsapPoolSocketAddress;
    private final boolean mUsapPoolSupported;
    private final LocalSocketAddress mZygoteSecondarySocketAddress;
    private final LocalSocketAddress mZygoteSocketAddress;
    private ZygoteState primaryZygoteState;
    private ZygoteState secondaryZygoteState;

    private static boolean policySpecifiesUsapPoolLaunch(int i) {
        return (i & 5) == 1;
    }

    public ZygoteProcess() {
        this.mLock = new Object();
        this.mApiDenylistExemptions = Collections.EMPTY_LIST;
        this.mUsapPoolEnabled = false;
        this.mIsFirstPropCheck = true;
        this.mLastPropCheckTimestamp = 0L;
        this.mZygoteSocketAddress = new LocalSocketAddress(Zygote.PRIMARY_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        this.mZygoteSecondarySocketAddress = new LocalSocketAddress(Zygote.SECONDARY_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSocketAddress = new LocalSocketAddress(Zygote.USAP_POOL_PRIMARY_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSecondarySocketAddress = new LocalSocketAddress(Zygote.USAP_POOL_SECONDARY_SOCKET_NAME, LocalSocketAddress.Namespace.RESERVED);
        this.mUsapPoolSupported = true;
    }

    public ZygoteProcess(LocalSocketAddress localSocketAddress, LocalSocketAddress localSocketAddress2) {
        this.mLock = new Object();
        this.mApiDenylistExemptions = Collections.EMPTY_LIST;
        this.mUsapPoolEnabled = false;
        this.mIsFirstPropCheck = true;
        this.mLastPropCheckTimestamp = 0L;
        this.mZygoteSocketAddress = localSocketAddress;
        this.mZygoteSecondarySocketAddress = localSocketAddress2;
        this.mUsapPoolSocketAddress = null;
        this.mUsapPoolSecondarySocketAddress = null;
        this.mUsapPoolSupported = false;
    }

    public LocalSocketAddress getPrimarySocketAddress() {
        return this.mZygoteSocketAddress;
    }

    private static class ZygoteState implements AutoCloseable {
        private final List<String> mAbiList;
        private boolean mClosed;
        final LocalSocketAddress mUsapSocketAddress;
        final DataInputStream mZygoteInputStream;
        final BufferedWriter mZygoteOutputWriter;
        private final LocalSocket mZygoteSessionSocket;
        final LocalSocketAddress mZygoteSocketAddress;

        private ZygoteState(LocalSocketAddress localSocketAddress, LocalSocketAddress localSocketAddress2, LocalSocket localSocket, DataInputStream dataInputStream, BufferedWriter bufferedWriter, List<String> list) {
            this.mZygoteSocketAddress = localSocketAddress;
            this.mUsapSocketAddress = localSocketAddress2;
            this.mZygoteSessionSocket = localSocket;
            this.mZygoteInputStream = dataInputStream;
            this.mZygoteOutputWriter = bufferedWriter;
            this.mAbiList = list;
        }

        static ZygoteState connect(LocalSocketAddress localSocketAddress, LocalSocketAddress localSocketAddress2) throws IOException {
            LocalSocket localSocket = new LocalSocket();
            if (localSocketAddress == null) {
                throw new IllegalArgumentException("zygoteSocketAddress can't be null");
            }
            try {
                localSocket.connect(localSocketAddress);
                DataInputStream dataInputStream = new DataInputStream(localSocket.getInputStream());
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(localSocket.getOutputStream()), 256);
                return new ZygoteState(localSocketAddress, localSocketAddress2, localSocket, dataInputStream, bufferedWriter, ZygoteProcess.getAbiList(bufferedWriter, dataInputStream));
            } catch (IOException e) {
                try {
                    localSocket.close();
                    throw e;
                } catch (IOException unused) {
                    throw e;
                }
            }
        }

        LocalSocket getUsapSessionSocket() throws IOException {
            LocalSocket localSocket = new LocalSocket();
            localSocket.connect(this.mUsapSocketAddress);
            return localSocket;
        }

        boolean matches(String str) {
            return this.mAbiList.contains(str);
        }

        @Override // java.lang.AutoCloseable
        public void close() {
            try {
                this.mZygoteSessionSocket.close();
            } catch (IOException e) {
                Log.e(ZygoteProcess.LOG_TAG, "I/O exception on routine close", e);
            }
            this.mClosed = true;
        }

        boolean isClosed() {
            return this.mClosed;
        }
    }

    public final Process.ProcessStartResult start(String str, String str2, int i, int i2, int[] iArr, int i3, int i4, int i5, String str3, String str4, String str5, String str6, String str7, String str8, int i6, boolean z, long[] jArr, Map<String, Pair<String, Long>> map, Map<String, Pair<String, Long>> map2, boolean z2, boolean z3, boolean z4, String[] strArr) {
        if (fetchUsapPoolEnabledPropWithMinInterval()) {
            informZygotesOfUsapPoolStatus();
        }
        try {
            return startViaZygote(str, str2, i, i2, iArr, i3, i4, i5, str3, str4, str5, str6, str7, false, str8, i6, z, jArr, map, map2, z2, z3, z4, strArr);
        } catch (ZygoteStartFailedEx e) {
            Log.e(LOG_TAG, "Starting VM process through Zygote failed");
            throw new RuntimeException("Starting VM process through Zygote failed", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<String> getAbiList(BufferedWriter bufferedWriter, DataInputStream dataInputStream) throws IOException {
        bufferedWriter.write("1");
        bufferedWriter.newLine();
        bufferedWriter.write("--query-abi-list");
        bufferedWriter.newLine();
        bufferedWriter.flush();
        byte[] bArr = new byte[dataInputStream.readInt()];
        dataInputStream.readFully(bArr);
        return Arrays.asList(new String(bArr, StandardCharsets.US_ASCII).split(","));
    }

    private Process.ProcessStartResult zygoteSendArgsAndGetResult(ZygoteState zygoteState, int i, ArrayList<String> arrayList) throws ZygoteStartFailedEx {
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            if (next.indexOf(10) >= 0) {
                throw new ZygoteStartFailedEx("Embedded newlines not allowed");
            }
            if (next.indexOf(13) >= 0) {
                throw new ZygoteStartFailedEx("Embedded carriage returns not allowed");
            }
            if (next.indexOf(0) >= 0) {
                throw new ZygoteStartFailedEx("Embedded nulls not allowed");
            }
        }
        String str = arrayList.size() + ShaderAssembler.NEWLINE + String.join(ShaderAssembler.NEWLINE, arrayList) + ShaderAssembler.NEWLINE;
        if (shouldAttemptUsapLaunch(i, arrayList)) {
            try {
                return attemptUsapSendArgsAndGetResult(zygoteState, str);
            } catch (IOException e) {
                Log.e(LOG_TAG, "IO Exception while communicating with USAP pool - " + e.getMessage());
            }
        }
        return this.attemptZygoteSendArgsAndGetResult(zygoteState, str);
    }

    private Process.ProcessStartResult attemptZygoteSendArgsAndGetResult(ZygoteState zygoteState, String str) throws ZygoteStartFailedEx, IOException {
        try {
            BufferedWriter bufferedWriter = zygoteState.mZygoteOutputWriter;
            DataInputStream dataInputStream = zygoteState.mZygoteInputStream;
            bufferedWriter.write(str);
            bufferedWriter.flush();
            Process.ProcessStartResult processStartResult = new Process.ProcessStartResult();
            processStartResult.pid = dataInputStream.readInt();
            processStartResult.usingWrapper = dataInputStream.readBoolean();
            if (processStartResult.pid >= 0) {
                return processStartResult;
            }
            throw new ZygoteStartFailedEx("fork() failed");
        } catch (IOException e) {
            zygoteState.close();
            Log.e(LOG_TAG, "IO Exception while communicating with Zygote - " + e.toString());
            throw new ZygoteStartFailedEx(e);
        }
    }

    private Process.ProcessStartResult attemptUsapSendArgsAndGetResult(ZygoteState zygoteState, String str) throws ZygoteStartFailedEx, IOException {
        LocalSocket usapSessionSocket = zygoteState.getUsapSessionSocket();
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(usapSessionSocket.getOutputStream()), 256);
            DataInputStream dataInputStream = new DataInputStream(usapSessionSocket.getInputStream());
            bufferedWriter.write(str);
            bufferedWriter.flush();
            Process.ProcessStartResult processStartResult = new Process.ProcessStartResult();
            processStartResult.pid = dataInputStream.readInt();
            processStartResult.usingWrapper = false;
            if (processStartResult.pid < 0) {
                throw new ZygoteStartFailedEx("USAP specialization failed");
            }
            if (usapSessionSocket != null) {
                usapSessionSocket.close();
            }
            return processStartResult;
        } catch (Throwable th) {
            if (usapSessionSocket != null) {
                try {
                    usapSessionSocket.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private boolean shouldAttemptUsapLaunch(int i, ArrayList<String> arrayList) {
        return this.mUsapPoolSupported && this.mUsapPoolEnabled && policySpecifiesUsapPoolLaunch(i) && commandSupportedByUsap(arrayList);
    }

    private static boolean commandSupportedByUsap(ArrayList<String> arrayList) {
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            String next = it.next();
            for (String str : INVALID_USAP_FLAGS) {
                if (next.startsWith(str)) {
                    return false;
                }
            }
            if (next.startsWith("--nice-name=") && Zygote.getWrapProperty(next.substring(12)) != null) {
                return false;
            }
        }
        return true;
    }

    private Process.ProcessStartResult startViaZygote(String str, String str2, int i, int i2, int[] iArr, int i3, int i4, int i5, String str3, String str4, String str5, String str6, String str7, boolean z, String str8, int i6, boolean z2, long[] jArr, Map<String, Pair<String, Long>> map, Map<String, Pair<String, Long>> map2, boolean z3, boolean z4, boolean z5, String[] strArr) throws ZygoteStartFailedEx {
        byte[] sEInfo;
        Process.ProcessStartResult processStartResultZygoteSendArgsAndGetResult;
        char c;
        char c2;
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("--runtime-args");
        arrayList.add("--setuid=" + i);
        arrayList.add("--setgid=" + i2);
        arrayList.add("--runtime-flags=" + i3);
        if (i4 == 1) {
            arrayList.add("--mount-external-default");
        } else if (i4 == 2) {
            arrayList.add("--mount-external-installer");
        } else if (i4 == 3) {
            arrayList.add("--mount-external-pass-through");
        } else if (i4 == 4) {
            arrayList.add("--mount-external-android-writable");
        }
        arrayList.add("--target-sdk-version=" + i5);
        char c3 = ',';
        if (iArr != null && iArr.length > 0) {
            StringBuilder sb = new StringBuilder("--setgroups=");
            int length = iArr.length;
            int i7 = 0;
            while (i7 < length) {
                if (i7 != 0) {
                    sb.append(c3);
                }
                sb.append(iArr[i7]);
                i7++;
                c3 = ',';
            }
            arrayList.add(sb.toString());
        }
        if (str2 != null) {
            arrayList.add("--nice-name=" + str2);
        }
        if (str3 != null) {
            arrayList.add("--seinfo=" + str3);
        }
        if (str5 != null) {
            arrayList.add("--instruction-set=" + str5);
        }
        if (str6 != null) {
            arrayList.add("--app-data-dir=" + str6);
        }
        if (str7 != null) {
            arrayList.add("--invoke-with");
            arrayList.add(str7);
        }
        if (z) {
            arrayList.add("--start-child-zygote");
        }
        if (str8 != null) {
            arrayList.add("--package-name=" + str8);
        }
        if (z2) {
            arrayList.add(Zygote.START_AS_TOP_APP_ARG);
        }
        if (map != null && map.size() > 0) {
            StringBuilder sb2 = new StringBuilder("--pkg-data-info-map=");
            boolean z6 = false;
            for (Map.Entry<String, Pair<String, Long>> entry : map.entrySet()) {
                if (z6) {
                    c2 = ',';
                    sb2.append(',');
                } else {
                    c2 = ',';
                }
                sb2.append(entry.getKey());
                sb2.append(c2);
                sb2.append(entry.getValue().first);
                sb2.append(c2);
                sb2.append(entry.getValue().second);
                z6 = true;
            }
            arrayList.add(sb2.toString());
        }
        if (map2 != null && map2.size() > 0) {
            StringBuilder sb3 = new StringBuilder("--allowlisted-data-info-map=");
            boolean z7 = false;
            for (Map.Entry<String, Pair<String, Long>> entry2 : map2.entrySet()) {
                if (z7) {
                    c = ',';
                    sb3.append(',');
                } else {
                    c = ',';
                }
                sb3.append(entry2.getKey());
                sb3.append(c);
                sb3.append(entry2.getValue().first);
                sb3.append(c);
                sb3.append(entry2.getValue().second);
                z7 = true;
            }
            arrayList.add(sb3.toString());
        }
        if (z4) {
            arrayList.add(Zygote.BIND_MOUNT_APP_STORAGE_DIRS);
        }
        if (z3) {
            arrayList.add(Zygote.BIND_MOUNT_APP_DATA_DIRS);
        }
        if (z5) {
            arrayList.add(Zygote.BIND_MOUNT_SYSPROP_OVERRIDES);
        }
        if (jArr != null && jArr.length > 0) {
            StringBuilder sb4 = new StringBuilder("--disabled-compat-changes=");
            int length2 = jArr.length;
            for (int i8 = 0; i8 < length2; i8++) {
                if (i8 != 0) {
                    sb4.append(',');
                }
                sb4.append(jArr[i8]);
            }
            arrayList.add(sb4.toString());
        }
        arrayList.add(str);
        if (strArr != null) {
            Collections.addAll(arrayList, strArr);
        }
        synchronized (this.mLock) {
            if (ASKSManager.hasBlockPolicy() && ASKSManager.isBlockTarget(i, str8)) {
                throw new ZygoteStartFailedEx("should be blocked by signaficant reasons - " + str8);
            }
            if (ASKSManager.isRestrictedTarget(str2, ASKSManager.TYPE_DENY)) {
                try {
                    sEInfo = ASKSManager.getASKSManager().getSEInfo(str2);
                } catch (RemoteException unused) {
                    sEInfo = null;
                }
                if (sEInfo != null && "aasa_blocked".equals(new String(sEInfo))) {
                    throw new ZygoteStartFailedEx("should be restricted by signaficant reasons - " + str8);
                }
            }
            processStartResultZygoteSendArgsAndGetResult = zygoteSendArgsAndGetResult(openZygoteSocketIfNeeded(str4), i6, arrayList);
            if (ASKSManager.isRestrictedTarget(str2, ASKSManager.TYPE_REVOKE)) {
                ASKSManager.addPackageWithPid(processStartResultZygoteSendArgsAndGetResult.pid, str2);
            }
        }
        return processStartResultZygoteSendArgsAndGetResult;
    }

    private boolean fetchUsapPoolEnabledProp() {
        boolean z = this.mUsapPoolEnabled;
        boolean bool = ZygoteConfig.getBool(ZygoteConfig.USAP_POOL_ENABLED, false);
        this.mUsapPoolEnabled = bool;
        boolean z2 = z != bool;
        if (z2) {
            Log.i(LOG_TAG, "usapPoolEnabled = " + this.mUsapPoolEnabled);
        }
        return z2;
    }

    private boolean fetchUsapPoolEnabledPropWithMinInterval() {
        if (!this.mUsapPoolSupported) {
            return false;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (!this.mIsFirstPropCheck && jElapsedRealtime - this.mLastPropCheckTimestamp < 60000) {
            return false;
        }
        this.mIsFirstPropCheck = false;
        this.mLastPropCheckTimestamp = jElapsedRealtime;
        return fetchUsapPoolEnabledProp();
    }

    public void close() {
        ZygoteState zygoteState = this.primaryZygoteState;
        if (zygoteState != null) {
            zygoteState.close();
        }
        ZygoteState zygoteState2 = this.secondaryZygoteState;
        if (zygoteState2 != null) {
            zygoteState2.close();
        }
    }

    public void establishZygoteConnectionForAbi(String str) {
        try {
            synchronized (this.mLock) {
                openZygoteSocketIfNeeded(str);
            }
        } catch (ZygoteStartFailedEx e) {
            throw new RuntimeException("Unable to connect to zygote for abi: " + str, e);
        }
    }

    public int getZygotePid(String str) {
        int i;
        try {
            synchronized (this.mLock) {
                ZygoteState zygoteStateOpenZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str);
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.write("1");
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.write("--get-pid");
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
                byte[] bArr = new byte[zygoteStateOpenZygoteSocketIfNeeded.mZygoteInputStream.readInt()];
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteInputStream.readFully(bArr);
                i = Integer.parseInt(new String(bArr, StandardCharsets.US_ASCII));
            }
            return i;
        } catch (Exception e) {
            throw new RuntimeException("Failure retrieving pid", e);
        }
    }

    public void bootCompleted() {
        if (Build.SUPPORTED_32_BIT_ABIS.length > 0) {
            bootCompleted(Build.SUPPORTED_32_BIT_ABIS[0]);
        }
        if (Build.SUPPORTED_64_BIT_ABIS.length > 0) {
            bootCompleted(Build.SUPPORTED_64_BIT_ABIS[0]);
        }
    }

    private void bootCompleted(String str) {
        try {
            synchronized (this.mLock) {
                ZygoteState zygoteStateOpenZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str);
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.write("1\n--boot-completed\n");
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteInputStream.readInt();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to inform zygote of boot_completed", e);
        }
    }

    public boolean setApiDenylistExemptions(List<String> list) {
        boolean zMaybeSetApiDenylistExemptions;
        synchronized (this.mLock) {
            this.mApiDenylistExemptions = list;
            zMaybeSetApiDenylistExemptions = maybeSetApiDenylistExemptions(this.primaryZygoteState, true);
            if (zMaybeSetApiDenylistExemptions) {
                zMaybeSetApiDenylistExemptions = maybeSetApiDenylistExemptions(this.secondaryZygoteState, true);
            }
        }
        return zMaybeSetApiDenylistExemptions;
    }

    public void setHiddenApiAccessLogSampleRate(int i) {
        synchronized (this.mLock) {
            this.mHiddenApiAccessLogSampleRate = i;
            maybeSetHiddenApiAccessLogSampleRate(this.primaryZygoteState);
            maybeSetHiddenApiAccessLogSampleRate(this.secondaryZygoteState);
        }
    }

    public void setHiddenApiAccessStatslogSampleRate(int i) {
        synchronized (this.mLock) {
            this.mHiddenApiAccessStatslogSampleRate = i;
            maybeSetHiddenApiAccessStatslogSampleRate(this.primaryZygoteState);
            maybeSetHiddenApiAccessStatslogSampleRate(this.secondaryZygoteState);
        }
    }

    private boolean maybeSetApiDenylistExemptions(ZygoteState zygoteState, boolean z) throws IOException {
        if (zygoteState == null || zygoteState.isClosed()) {
            Slog.e(LOG_TAG, "Can't set API denylist exemptions: no zygote connection");
            return false;
        }
        if (!z && this.mApiDenylistExemptions.isEmpty()) {
            return true;
        }
        for (String str : this.mApiDenylistExemptions) {
            if (str.indexOf(10) >= 0 || str.indexOf(13) >= 0 || str.indexOf(0) >= 0) {
                Slog.e(LOG_TAG, "Failed to set API denylist exemptions: Bad character");
                this.mApiDenylistExemptions = Collections.EMPTY_LIST;
                return false;
            }
        }
        try {
            zygoteState.mZygoteOutputWriter.write(Integer.toString(this.mApiDenylistExemptions.size() + 1));
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.write("--set-api-denylist-exemptions");
            zygoteState.mZygoteOutputWriter.newLine();
            for (int i = 0; i < this.mApiDenylistExemptions.size(); i++) {
                zygoteState.mZygoteOutputWriter.write(this.mApiDenylistExemptions.get(i));
                zygoteState.mZygoteOutputWriter.newLine();
            }
            zygoteState.mZygoteOutputWriter.flush();
            int i2 = zygoteState.mZygoteInputStream.readInt();
            if (i2 != 0) {
                Slog.e(LOG_TAG, "Failed to set API denylist exemptions; status " + i2);
            }
            return true;
        } catch (IOException e) {
            Slog.e(LOG_TAG, "Failed to set API denylist exemptions", e);
            this.mApiDenylistExemptions = Collections.EMPTY_LIST;
            return false;
        }
    }

    private void maybeSetHiddenApiAccessLogSampleRate(ZygoteState zygoteState) throws IOException {
        if (zygoteState == null || zygoteState.isClosed() || this.mHiddenApiAccessLogSampleRate == -1) {
            return;
        }
        try {
            zygoteState.mZygoteOutputWriter.write(Integer.toString(1));
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.write("--hidden-api-log-sampling-rate=" + this.mHiddenApiAccessLogSampleRate);
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.flush();
            int i = zygoteState.mZygoteInputStream.readInt();
            if (i != 0) {
                Slog.e(LOG_TAG, "Failed to set hidden API log sampling rate; status " + i);
            }
        } catch (IOException e) {
            Slog.e(LOG_TAG, "Failed to set hidden API log sampling rate", e);
        }
    }

    private void maybeSetHiddenApiAccessStatslogSampleRate(ZygoteState zygoteState) throws IOException {
        if (zygoteState == null || zygoteState.isClosed() || this.mHiddenApiAccessStatslogSampleRate == -1) {
            return;
        }
        try {
            zygoteState.mZygoteOutputWriter.write(Integer.toString(1));
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.write("--hidden-api-statslog-sampling-rate=" + this.mHiddenApiAccessStatslogSampleRate);
            zygoteState.mZygoteOutputWriter.newLine();
            zygoteState.mZygoteOutputWriter.flush();
            int i = zygoteState.mZygoteInputStream.readInt();
            if (i != 0) {
                Slog.e(LOG_TAG, "Failed to set hidden API statslog sampling rate; status " + i);
            }
        } catch (IOException e) {
            Slog.e(LOG_TAG, "Failed to set hidden API statslog sampling rate", e);
        }
    }

    private void attemptConnectionToPrimaryZygote() throws IOException {
        ZygoteState zygoteState = this.primaryZygoteState;
        if (zygoteState == null || zygoteState.isClosed()) {
            ZygoteState zygoteStateConnect = ZygoteState.connect(this.mZygoteSocketAddress, this.mUsapPoolSocketAddress);
            this.primaryZygoteState = zygoteStateConnect;
            maybeSetApiDenylistExemptions(zygoteStateConnect, false);
            maybeSetHiddenApiAccessLogSampleRate(this.primaryZygoteState);
        }
    }

    private void attemptConnectionToSecondaryZygote() throws IOException {
        ZygoteState zygoteState = this.secondaryZygoteState;
        if (zygoteState == null || zygoteState.isClosed()) {
            ZygoteState zygoteStateConnect = ZygoteState.connect(this.mZygoteSecondarySocketAddress, this.mUsapPoolSecondarySocketAddress);
            this.secondaryZygoteState = zygoteStateConnect;
            maybeSetApiDenylistExemptions(zygoteStateConnect, false);
            maybeSetHiddenApiAccessLogSampleRate(this.secondaryZygoteState);
        }
    }

    private ZygoteState openZygoteSocketIfNeeded(String str) throws ZygoteStartFailedEx {
        try {
            attemptConnectionToPrimaryZygote();
            if (this.primaryZygoteState.matches(str)) {
                return this.primaryZygoteState;
            }
            if (this.mZygoteSecondarySocketAddress != null) {
                attemptConnectionToSecondaryZygote();
                if (this.secondaryZygoteState.matches(str)) {
                    return this.secondaryZygoteState;
                }
            }
            throw new ZygoteStartFailedEx("Unsupported zygote ABI: " + str);
        } catch (IOException e) {
            throw new ZygoteStartFailedEx("Error connecting to zygote", e);
        }
    }

    public static void setAppZygotePreloadTimeout(int i) {
        Slog.i(LOG_TAG, "Changing app-zygote preload timeout to " + i + " ms.");
        sAppZygotePreloadTimeoutMs = i;
    }

    public boolean preloadApp(ApplicationInfo applicationInfo, String str) throws ZygoteStartFailedEx, IOException {
        boolean z;
        synchronized (this.mLock) {
            ZygoteState zygoteStateOpenZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str);
            int soTimeout = zygoteStateOpenZygoteSocketIfNeeded.mZygoteSessionSocket.getSoTimeout();
            try {
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteSessionSocket.setSoTimeout(sAppZygotePreloadTimeoutMs);
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.write("2");
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.write("--preload-app");
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
                Parcel parcelObtain = Parcel.obtain();
                applicationInfo.writeToParcel(parcelObtain, 0);
                String strEncodeToString = Base64.getEncoder().encodeToString(parcelObtain.marshall());
                parcelObtain.recycle();
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.write(strEncodeToString);
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
                z = zygoteStateOpenZygoteSocketIfNeeded.mZygoteInputStream.readInt() == 0;
            } finally {
                zygoteStateOpenZygoteSocketIfNeeded.mZygoteSessionSocket.setSoTimeout(soTimeout);
            }
        }
        return z;
    }

    public boolean preloadDefault(String str) throws ZygoteStartFailedEx, IOException {
        boolean z;
        synchronized (this.mLock) {
            ZygoteState zygoteStateOpenZygoteSocketIfNeeded = openZygoteSocketIfNeeded(str);
            zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.write("1");
            zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.write("--preload-default");
            zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.newLine();
            zygoteStateOpenZygoteSocketIfNeeded.mZygoteOutputWriter.flush();
            z = zygoteStateOpenZygoteSocketIfNeeded.mZygoteInputStream.readInt() == 0;
        }
        return z;
    }

    public static void waitForConnectionToZygote(String str) throws InterruptedException {
        waitForConnectionToZygote(new LocalSocketAddress(str, LocalSocketAddress.Namespace.RESERVED));
    }

    public static void waitForConnectionToZygote(LocalSocketAddress localSocketAddress) throws InterruptedException {
        for (int i = 1200; i >= 0; i--) {
            try {
                ZygoteState.connect(localSocketAddress, null).close();
                return;
            } catch (IOException e) {
                Log.w(LOG_TAG, "Got error connecting to zygote, retrying. msg= " + e.getMessage());
                try {
                    Thread.sleep(50L);
                } catch (InterruptedException unused) {
                }
            }
        }
        Slog.wtf(LOG_TAG, "Failed to connect to Zygote through socket " + localSocketAddress.getName());
    }

    private void informZygotesOfUsapPoolStatus() {
        String str = "1\n--usap-pool-enabled=" + this.mUsapPoolEnabled + ShaderAssembler.NEWLINE;
        synchronized (this.mLock) {
            try {
                try {
                    attemptConnectionToPrimaryZygote();
                    this.primaryZygoteState.mZygoteOutputWriter.write(str);
                    this.primaryZygoteState.mZygoteOutputWriter.flush();
                    if (this.mZygoteSecondarySocketAddress != null) {
                        try {
                            attemptConnectionToSecondaryZygote();
                            try {
                                this.secondaryZygoteState.mZygoteOutputWriter.write(str);
                                this.secondaryZygoteState.mZygoteOutputWriter.flush();
                                this.secondaryZygoteState.mZygoteInputStream.readInt();
                            } catch (IOException e) {
                                throw new IllegalStateException("USAP pool state change cause an irrecoverable error", e);
                            }
                        } catch (IOException unused) {
                        }
                    }
                    try {
                        this.primaryZygoteState.mZygoteInputStream.readInt();
                    } catch (IOException e2) {
                        throw new IllegalStateException("USAP pool state change cause an irrecoverable error", e2);
                    }
                } catch (IOException e3) {
                    this.mUsapPoolEnabled = !this.mUsapPoolEnabled;
                    Log.w(LOG_TAG, "Failed to inform zygotes of USAP pool status: " + e3.getMessage());
                }
            } finally {
            }
        }
    }

    public ChildZygoteProcess startChildZygote(String str, String str2, int i, int i2, int[] iArr, int i3, String str3, String str4, String str5, String str6, int i4, int i5) {
        LocalSocketAddress localSocketAddress = new LocalSocketAddress(str + "/" + UUID.randomUUID().toString());
        try {
            return new ChildZygoteProcess(localSocketAddress, startViaZygote(str, str2, i, i2, iArr, i3, 0, 0, str3, str4, str6, null, null, true, null, 4, false, null, null, null, true, false, false, new String[]{Zygote.CHILD_ZYGOTE_SOCKET_NAME_ARG + localSocketAddress.getName(), Zygote.CHILD_ZYGOTE_ABI_LIST_ARG + str5, Zygote.CHILD_ZYGOTE_UID_RANGE_START + i4, Zygote.CHILD_ZYGOTE_UID_RANGE_END + i5}).pid, i);
        } catch (ZygoteStartFailedEx e) {
            throw new RuntimeException("Starting child-zygote through Zygote failed", e);
        }
    }
}
