package android.hardware.location;

import android.annotation.SystemApi;
import android.chre.flags.Flags;
import android.content.Intent;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.util.Objects;

@SystemApi
/* loaded from: classes2.dex */
public class ContextHubIntentEvent {
    private final int mClientAuthorizationState;
    private final ContextHubInfo mContextHubInfo;
    private final int mEventType;
    private final int mNanoAppAbortCode;
    private final long mNanoAppId;
    private final NanoAppMessage mNanoAppMessage;

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int i, long j, NanoAppMessage nanoAppMessage, int i2, int i3) {
        this.mContextHubInfo = contextHubInfo;
        this.mEventType = i;
        this.mNanoAppId = j;
        this.mNanoAppMessage = nanoAppMessage;
        this.mNanoAppAbortCode = i2;
        this.mClientAuthorizationState = i3;
    }

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int i) {
        this(contextHubInfo, i, -1L, null, -1, 0);
    }

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int i, long j) {
        this(contextHubInfo, i, j, null, -1, 0);
    }

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int i, long j, NanoAppMessage nanoAppMessage) {
        this(contextHubInfo, i, j, nanoAppMessage, -1, 0);
    }

    private ContextHubIntentEvent(ContextHubInfo contextHubInfo, int i, long j, int i2) {
        this(contextHubInfo, i, j, null, i2, 0);
    }

    public static ContextHubIntentEvent fromIntent(Intent intent) {
        Objects.requireNonNull(intent, "Intent cannot be null");
        hasExtraOrThrow(intent, ContextHubManager.EXTRA_CONTEXT_HUB_INFO);
        ContextHubInfo contextHubInfo = (ContextHubInfo) intent.getParcelableExtra(ContextHubManager.EXTRA_CONTEXT_HUB_INFO, ContextHubInfo.class);
        if (contextHubInfo == null) {
            throw new IllegalArgumentException("ContextHubInfo extra was null");
        }
        int intExtraOrThrow = getIntExtraOrThrow(intent, ContextHubManager.EXTRA_EVENT_TYPE);
        switch (intExtraOrThrow) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 7:
                long longExtraOrThrow = getLongExtraOrThrow(intent, ContextHubManager.EXTRA_NANOAPP_ID);
                if (intExtraOrThrow == 5) {
                    hasExtraOrThrow(intent, ContextHubManager.EXTRA_MESSAGE);
                    NanoAppMessage nanoAppMessage = (NanoAppMessage) intent.getParcelableExtra(ContextHubManager.EXTRA_MESSAGE, NanoAppMessage.class);
                    if (nanoAppMessage == null) {
                        throw new IllegalArgumentException("NanoAppMessage extra was null");
                    }
                    return new ContextHubIntentEvent(contextHubInfo, intExtraOrThrow, longExtraOrThrow, nanoAppMessage);
                }
                if (intExtraOrThrow == 4) {
                    return new ContextHubIntentEvent(contextHubInfo, intExtraOrThrow, longExtraOrThrow, getIntExtraOrThrow(intent, ContextHubManager.EXTRA_NANOAPP_ABORT_CODE));
                }
                if (intExtraOrThrow == 7) {
                    return new ContextHubIntentEvent(contextHubInfo, intExtraOrThrow, longExtraOrThrow, null, -1, getIntExtraOrThrow(intent, ContextHubManager.EXTRA_CLIENT_AUTHORIZATION_STATE));
                }
                return new ContextHubIntentEvent(contextHubInfo, intExtraOrThrow, longExtraOrThrow);
            case 6:
                return new ContextHubIntentEvent(contextHubInfo, intExtraOrThrow);
            default:
                throw new IllegalArgumentException("Unknown intent event type " + intExtraOrThrow);
        }
    }

    public int getEventType() {
        return this.mEventType;
    }

    public ContextHubInfo getContextHubInfo() {
        return this.mContextHubInfo;
    }

    public long getNanoAppId() {
        if (this.mEventType == 6) {
            throw new UnsupportedOperationException("Cannot invoke getNanoAppId() on Context Hub reset event");
        }
        return this.mNanoAppId;
    }

    public int getNanoAppAbortCode() {
        if (this.mEventType != 4) {
            throw new UnsupportedOperationException("Cannot invoke getNanoAppAbortCode() on non-abort event: " + this.mEventType);
        }
        return this.mNanoAppAbortCode;
    }

    public NanoAppMessage getNanoAppMessage() {
        if (this.mEventType != 5) {
            throw new UnsupportedOperationException("Cannot invoke getNanoAppMessage() on non-message event: " + this.mEventType);
        }
        return this.mNanoAppMessage;
    }

    public int getClientAuthorizationState() {
        if (this.mEventType != 7) {
            throw new UnsupportedOperationException("Cannot invoke getClientAuthorizationState() on non-authorization event: " + this.mEventType);
        }
        return this.mClientAuthorizationState;
    }

    public String toString() {
        String str = "ContextHubIntentEvent[eventType = " + this.mEventType + ", contextHubId = " + this.mContextHubInfo.getId();
        if (this.mEventType != 6) {
            str = str + ", nanoAppId = 0x" + Long.toHexString(this.mNanoAppId);
        }
        if (this.mEventType == 4) {
            str = str + ", nanoAppAbortCode = " + this.mNanoAppAbortCode;
        }
        if (this.mEventType == 5) {
            str = str + ", nanoAppMessage = " + this.mNanoAppMessage;
        }
        if (this.mEventType == 7) {
            str = str + ", clientAuthState = " + this.mClientAuthorizationState;
        }
        return str + NavigationBarInflaterView.SIZE_MOD_END;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0037 A[Catch: UnsupportedOperationException -> 0x0066, TryCatch #0 {UnsupportedOperationException -> 0x0066, blocks: (B:12:0x001f, B:14:0x0024, B:18:0x0032, B:20:0x0037, B:23:0x0042, B:25:0x0043, B:27:0x0048, B:28:0x0053, B:30:0x0058), top: B:11:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0048 A[Catch: UnsupportedOperationException -> 0x0066, TryCatch #0 {UnsupportedOperationException -> 0x0066, blocks: (B:12:0x001f, B:14:0x0024, B:18:0x0032, B:20:0x0037, B:23:0x0042, B:25:0x0043, B:27:0x0048, B:28:0x0053, B:30:0x0058), top: B:11:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0058 A[Catch: UnsupportedOperationException -> 0x0066, TRY_LEAVE, TryCatch #0 {UnsupportedOperationException -> 0x0066, blocks: (B:12:0x001f, B:14:0x0024, B:18:0x0032, B:20:0x0037, B:23:0x0042, B:25:0x0043, B:27:0x0048, B:28:0x0053, B:30:0x0058), top: B:11:0x001f }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0065 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean equals(java.lang.Object r8) {
        /*
            r7 = this;
            r0 = 1
            if (r8 != r7) goto L4
            return r0
        L4:
            boolean r1 = r8 instanceof android.hardware.location.ContextHubIntentEvent
            r2 = 0
            if (r1 == 0) goto L66
            android.hardware.location.ContextHubIntentEvent r8 = (android.hardware.location.ContextHubIntentEvent) r8
            int r1 = r8.getEventType()
            int r3 = r7.mEventType
            if (r1 != r3) goto L66
            android.hardware.location.ContextHubInfo r1 = r8.getContextHubInfo()
            android.hardware.location.ContextHubInfo r3 = r7.mContextHubInfo
            boolean r1 = r1.equals(r3)
            if (r1 == 0) goto L66
            int r1 = r7.mEventType     // Catch: java.lang.UnsupportedOperationException -> L66
            r3 = 6
            if (r1 == r3) goto L31
            long r3 = r8.getNanoAppId()     // Catch: java.lang.UnsupportedOperationException -> L66
            long r5 = r7.mNanoAppId     // Catch: java.lang.UnsupportedOperationException -> L66
            int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r1 != 0) goto L2f
            goto L31
        L2f:
            r1 = r2
            goto L32
        L31:
            r1 = r0
        L32:
            int r3 = r7.mEventType     // Catch: java.lang.UnsupportedOperationException -> L66
            r4 = 4
            if (r3 != r4) goto L43
            int r3 = r8.getNanoAppAbortCode()     // Catch: java.lang.UnsupportedOperationException -> L66
            int r4 = r7.mNanoAppAbortCode     // Catch: java.lang.UnsupportedOperationException -> L66
            if (r3 != r4) goto L41
            r3 = r0
            goto L42
        L41:
            r3 = r2
        L42:
            r1 = r1 & r3
        L43:
            int r3 = r7.mEventType     // Catch: java.lang.UnsupportedOperationException -> L66
            r4 = 5
            if (r3 != r4) goto L53
            android.hardware.location.NanoAppMessage r3 = r8.getNanoAppMessage()     // Catch: java.lang.UnsupportedOperationException -> L66
            android.hardware.location.NanoAppMessage r4 = r7.mNanoAppMessage     // Catch: java.lang.UnsupportedOperationException -> L66
            boolean r3 = r3.equals(r4)     // Catch: java.lang.UnsupportedOperationException -> L66
            r1 = r1 & r3
        L53:
            int r3 = r7.mEventType     // Catch: java.lang.UnsupportedOperationException -> L66
            r4 = 7
            if (r3 != r4) goto L65
            int r8 = r8.getClientAuthorizationState()     // Catch: java.lang.UnsupportedOperationException -> L66
            int r7 = r7.mClientAuthorizationState     // Catch: java.lang.UnsupportedOperationException -> L66
            if (r8 != r7) goto L61
            goto L62
        L61:
            r0 = r2
        L62:
            r7 = r1 & r0
            return r7
        L65:
            return r1
        L66:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.hardware.location.ContextHubIntentEvent.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        if (!Flags.fixApiCheck()) {
            return super.hashCode();
        }
        return Objects.hash(Integer.valueOf(this.mEventType), this.mContextHubInfo, Long.valueOf(this.mNanoAppId), this.mNanoAppMessage, Integer.valueOf(this.mNanoAppAbortCode), Integer.valueOf(this.mClientAuthorizationState));
    }

    private static void hasExtraOrThrow(Intent intent, String str) {
        if (intent.hasExtra(str)) {
            return;
        }
        throw new IllegalArgumentException("Intent did not have extra: " + str);
    }

    private static int getIntExtraOrThrow(Intent intent, String str) {
        hasExtraOrThrow(intent, str);
        return intent.getIntExtra(str, -1);
    }

    private static long getLongExtraOrThrow(Intent intent, String str) {
        hasExtraOrThrow(intent, str);
        return intent.getLongExtra(str, -1L);
    }
}
