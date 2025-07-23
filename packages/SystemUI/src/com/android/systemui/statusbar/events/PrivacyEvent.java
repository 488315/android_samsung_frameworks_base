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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PrivacyEvent implements StatusEvent {
    public String contentDescription;
    public boolean forceVisible;
    public final int priority;
    public OngoingPrivacyChip privacyChip;
    public List privacyItems;
    public final boolean showAnimation;
    public final PrivacyEvent$$ExternalSyntheticLambda0 viewCreator;

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
        if (!(statusEvent instanceof PrivacyEvent)) {
            return false;
        }
        PrivacyEvent privacyEvent = (PrivacyEvent) statusEvent;
        if (Intrinsics.areEqual(privacyEvent.privacyItems, this.privacyItems) && Intrinsics.areEqual(privacyEvent.contentDescription, this.contentDescription)) {
            return privacyEvent.forceVisible && !this.forceVisible;
        }
        return true;
    }

    public final String toString() {
        return getClass().getSimpleName() + "(forceVisible=" + this.forceVisible + ", privacyItems=" + this.privacyItems + ")";
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

    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.systemui.statusbar.events.PrivacyEvent$$ExternalSyntheticLambda0] */
    public PrivacyEvent(boolean z) {
        this.showAnimation = z;
        this.priority = 100;
        this.forceVisible = true;
        this.privacyItems = EmptyList.INSTANCE;
        this.viewCreator = new Function1() { // from class: com.android.systemui.statusbar.events.PrivacyEvent$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                OngoingPrivacyChip ongoingPrivacyChip = new OngoingPrivacyChip(new ContextThemeWrapper((Context) obj, R.style.SamsungOngoingPrivacyChip), null, 0, 0, 14, null);
                PrivacyEvent privacyEvent = PrivacyEvent.this;
                ongoingPrivacyChip.setPrivacyList(privacyEvent.privacyItems);
                ongoingPrivacyChip.setContentDescription(privacyEvent.contentDescription);
                privacyEvent.privacyChip = ongoingPrivacyChip;
                return ongoingPrivacyChip;
            }
        };
    }

    public /* synthetic */ PrivacyEvent(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? true : z);
    }
}
