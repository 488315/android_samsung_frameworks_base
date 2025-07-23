package android.service.autofill;

import android.widget.RemoteViews;

/* loaded from: classes3.dex */
public final class Presentations {
    private RemoteViews mDialogPresentation;
    private InlinePresentation mInlinePresentation;
    private InlinePresentation mInlineTooltipPresentation;
    private RemoteViews mMenuPresentation;

    @Deprecated
    private void __metadata() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static RemoteViews defaultDialogPresentation() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static InlinePresentation defaultInlinePresentation() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static InlinePresentation defaultInlineTooltipPresentation() {
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static RemoteViews defaultMenuPresentation() {
        return null;
    }

    private void onConstructed() {
        if (this.mMenuPresentation == null && this.mInlinePresentation == null && this.mDialogPresentation == null) {
            throw new IllegalStateException("All presentations are null.");
        }
        if (this.mInlineTooltipPresentation != null && this.mInlinePresentation == null) {
            throw new IllegalStateException("The inline presentation is required for mInlineTooltipPresentation.");
        }
    }

    Presentations(RemoteViews remoteViews, InlinePresentation inlinePresentation, RemoteViews remoteViews2, InlinePresentation inlinePresentation2) {
        this.mMenuPresentation = remoteViews;
        this.mInlinePresentation = inlinePresentation;
        this.mDialogPresentation = remoteViews2;
        this.mInlineTooltipPresentation = inlinePresentation2;
        onConstructed();
    }

    public RemoteViews getMenuPresentation() {
        return this.mMenuPresentation;
    }

    public InlinePresentation getInlinePresentation() {
        return this.mInlinePresentation;
    }

    public RemoteViews getDialogPresentation() {
        return this.mDialogPresentation;
    }

    public InlinePresentation getInlineTooltipPresentation() {
        return this.mInlineTooltipPresentation;
    }

    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private RemoteViews mDialogPresentation;
        private InlinePresentation mInlinePresentation;
        private InlinePresentation mInlineTooltipPresentation;
        private RemoteViews mMenuPresentation;

        public Builder setMenuPresentation(RemoteViews remoteViews) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mMenuPresentation = remoteViews;
            return this;
        }

        public Builder setInlinePresentation(InlinePresentation inlinePresentation) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mInlinePresentation = inlinePresentation;
            return this;
        }

        public Builder setDialogPresentation(RemoteViews remoteViews) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mDialogPresentation = remoteViews;
            return this;
        }

        public Builder setInlineTooltipPresentation(InlinePresentation inlinePresentation) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mInlineTooltipPresentation = inlinePresentation;
            return this;
        }

        public Presentations build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 16;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mMenuPresentation = Presentations.defaultMenuPresentation();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mInlinePresentation = Presentations.defaultInlinePresentation();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mDialogPresentation = Presentations.defaultDialogPresentation();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mInlineTooltipPresentation = Presentations.defaultInlineTooltipPresentation();
            }
            return new Presentations(this.mMenuPresentation, this.mInlinePresentation, this.mDialogPresentation, this.mInlineTooltipPresentation);
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }
}
