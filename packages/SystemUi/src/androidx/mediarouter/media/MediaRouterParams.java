package androidx.mediarouter.media;

import android.os.Bundle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MediaRouterParams {
    public final int mDialogType;
    public final Bundle mExtras;
    public final boolean mMediaTransferReceiverEnabled;
    public final boolean mOutputSwitcherEnabled;
    public final boolean mTransferToLocalEnabled;

    public MediaRouterParams(Builder builder) {
        this.mDialogType = builder.mDialogType;
        this.mMediaTransferReceiverEnabled = builder.mMediaTransferEnabled;
        this.mOutputSwitcherEnabled = builder.mOutputSwitcherEnabled;
        this.mTransferToLocalEnabled = builder.mTransferToLocalEnabled;
        Bundle bundle = builder.mExtras;
        this.mExtras = bundle == null ? Bundle.EMPTY : new Bundle(bundle);
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public final int mDialogType;
        public final Bundle mExtras;
        public final boolean mMediaTransferEnabled;
        public final boolean mOutputSwitcherEnabled;
        public final boolean mTransferToLocalEnabled;

        public Builder() {
            this.mDialogType = 1;
            this.mMediaTransferEnabled = true;
        }

        public Builder(MediaRouterParams mediaRouterParams) {
            this.mDialogType = 1;
            this.mMediaTransferEnabled = true;
            if (mediaRouterParams != null) {
                this.mDialogType = mediaRouterParams.mDialogType;
                this.mOutputSwitcherEnabled = mediaRouterParams.mOutputSwitcherEnabled;
                this.mTransferToLocalEnabled = mediaRouterParams.mTransferToLocalEnabled;
                this.mMediaTransferEnabled = mediaRouterParams.mMediaTransferReceiverEnabled;
                Bundle bundle = mediaRouterParams.mExtras;
                this.mExtras = bundle == null ? null : new Bundle(bundle);
                return;
            }
            throw new NullPointerException("params should not be null!");
        }
    }
}
