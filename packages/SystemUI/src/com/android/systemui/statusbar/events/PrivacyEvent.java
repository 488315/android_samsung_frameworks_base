package com.android.systemui.statusbar.events;

import android.content.Context;
import android.view.ContextThemeWrapper;
import com.android.systemui.R;
import com.android.systemui.privacy.OngoingPrivacyChip;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class PrivacyEvent implements StatusEvent {
    public String contentDescription;
    public boolean forceVisible;
    public final int priority;
    public OngoingPrivacyChip privacyChip;
    public List privacyItems;
    public final boolean showAnimation;
    public final Function1 viewCreator;

    public PrivacyEvent() {
        this(false, 1, null);
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final String getContentDescription() {
        return this.contentDescription;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final boolean getForceVisible() {
        return this.forceVisible;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final int getPriority() {
        return this.priority;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final boolean getShowAnimation() {
        return this.showAnimation;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final Function1 getViewCreator() {
        return this.viewCreator;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final void setForceVisible() {
        this.forceVisible = false;
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final boolean shouldUpdateFromEvent(StatusEvent statusEvent) {
        if (statusEvent instanceof PrivacyEvent) {
            PrivacyEvent privacyEvent = (PrivacyEvent) statusEvent;
            if (!Intrinsics.areEqual(privacyEvent.privacyItems, this.privacyItems) || !Intrinsics.areEqual(privacyEvent.contentDescription, this.contentDescription) || (privacyEvent.forceVisible && !this.forceVisible)) {
                return true;
            }
        }
        return false;
    }

    public final String toString() {
        return "PrivacyEvent(forceVisible=" + this.forceVisible + ", privacyItems=" + this.privacyItems + ")";
    }

    @Override // com.android.systemui.statusbar.events.StatusEvent
    public final void updateFromEvent(StatusEvent statusEvent) {
        if (statusEvent instanceof PrivacyEvent) {
            PrivacyEvent privacyEvent = (PrivacyEvent) statusEvent;
            this.privacyItems = privacyEvent.privacyItems;
            this.contentDescription = privacyEvent.contentDescription;
            OngoingPrivacyChip ongoingPrivacyChip = this.privacyChip;
            if (ongoingPrivacyChip != null) {
                ongoingPrivacyChip.setContentDescription(privacyEvent.contentDescription);
            }
            OngoingPrivacyChip ongoingPrivacyChip2 = this.privacyChip;
            if (ongoingPrivacyChip2 != null) {
                ongoingPrivacyChip2.setPrivacyList(privacyEvent.privacyItems);
            }
            if (privacyEvent.forceVisible) {
                this.forceVisible = true;
            }
        }
    }

    public PrivacyEvent(boolean z) {
        this.showAnimation = z;
        this.priority = 100;
        this.forceVisible = true;
        this.privacyItems = EmptyList.INSTANCE;
        this.viewCreator = new Function1() { // from class: com.android.systemui.statusbar.events.PrivacyEvent$viewCreator$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                OngoingPrivacyChip ongoingPrivacyChip = new OngoingPrivacyChip(new ContextThemeWrapper((Context) obj, R.style.SamsungOngoingPrivacyChip), null, 0, 0, 14, null);
                ongoingPrivacyChip.setPrivacyList(PrivacyEvent.this.privacyItems);
                ongoingPrivacyChip.setContentDescription(PrivacyEvent.this.contentDescription);
                PrivacyEvent.this.privacyChip = ongoingPrivacyChip;
                return ongoingPrivacyChip;
            }
        };
    }

    public /* synthetic */ PrivacyEvent(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }
}
