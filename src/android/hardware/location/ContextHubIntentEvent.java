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

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ContextHubIntentEvent) {
            ContextHubIntentEvent contextHubIntentEvent = (ContextHubIntentEvent) obj;
            if (contextHubIntentEvent.getEventType() == this.mEventType && contextHubIntentEvent.getContextHubInfo().equals(this.mContextHubInfo)) {
                try {
                    boolean zEquals = this.mEventType == 6 || contextHubIntentEvent.getNanoAppId() == this.mNanoAppId;
                    if (this.mEventType == 4) {
                        zEquals &= contextHubIntentEvent.getNanoAppAbortCode() == this.mNanoAppAbortCode;
                    }
                    if (this.mEventType == 5) {
                        zEquals &= contextHubIntentEvent.getNanoAppMessage().equals(this.mNanoAppMessage);
                    }
                    if (this.mEventType == 7) {
                        return zEquals & (contextHubIntentEvent.getClientAuthorizationState() == this.mClientAuthorizationState);
                    }
                    return zEquals;
                } catch (UnsupportedOperationException unused) {
                }
            }
        }
        return false;
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
