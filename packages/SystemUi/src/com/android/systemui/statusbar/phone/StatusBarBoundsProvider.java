package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.systemui.util.ConvenienceExtensionsKt;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarBoundsProvider {
    public final Set changeListeners;
    public final View endSideContent;
    public final StatusBarBoundsProvider$layoutListener$1 layoutListener = new View.OnLayoutChangeListener() { // from class: com.android.systemui.statusbar.phone.StatusBarBoundsProvider$layoutListener$1
        @Override // android.view.View.OnLayoutChangeListener
        public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            BoundsPair boundsPair = new BoundsPair(ConvenienceExtensionsKt.getBoundsOnScreen(StatusBarBoundsProvider.this.startSideContent), ConvenienceExtensionsKt.getBoundsOnScreen(StatusBarBoundsProvider.this.endSideContent));
            if (Intrinsics.areEqual(StatusBarBoundsProvider.this.previousBounds, boundsPair)) {
                return;
            }
            StatusBarBoundsProvider statusBarBoundsProvider = StatusBarBoundsProvider.this;
            statusBarBoundsProvider.previousBounds = boundsPair;
            for (SystemBarAttributesListener systemBarAttributesListener : statusBarBoundsProvider.changeListeners) {
                SystemBarAttributesParams systemBarAttributesParams = systemBarAttributesListener.lastSystemBarAttributesParams;
                if (systemBarAttributesParams != null) {
                    LetterboxDetails[] letterboxDetailsArr = systemBarAttributesParams.letterboxesArray;
                    if (!(letterboxDetailsArr.length == 0)) {
                        systemBarAttributesListener.onSystemBarAttributesChanged(systemBarAttributesParams.displayId, systemBarAttributesParams.appearance, systemBarAttributesParams.appearanceRegionsArray, systemBarAttributesParams.navbarColorManagedByIme, systemBarAttributesParams.behavior, systemBarAttributesParams.requestedVisibleTypes, systemBarAttributesParams.packageName, letterboxDetailsArr);
                    }
                }
            }
        }
    };
    public BoundsPair previousBounds;
    public final View startSideContent;

    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.phone.StatusBarBoundsProvider$layoutListener$1] */
    public StatusBarBoundsProvider(Set<SystemBarAttributesListener> set, View view, View view2) {
        this.changeListeners = set;
        this.startSideContent = view;
        this.endSideContent = view2;
        this.previousBounds = new BoundsPair(ConvenienceExtensionsKt.getBoundsOnScreen(view), ConvenienceExtensionsKt.getBoundsOnScreen(view2));
    }
}
