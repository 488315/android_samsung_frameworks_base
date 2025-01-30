package com.android.systemui.keyguard.ui.viewmodel;

import android.content.Context;
import com.android.systemui.keyguard.data.repository.KeyguardClockRepository$special$$inlined$map$1;
import com.android.systemui.keyguard.domain.interactor.KeyguardClockInteractor;
import com.android.systemui.keyguard.shared.model.SettingsClockSize;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardPreviewSmartspaceViewModel {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public final KeyguardPreviewSmartspaceViewModel$special$$inlined$map$2 shouldHideSmartspace;
    public final KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1 smartspaceTopPadding;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[SettingsClockSize.values().length];
            try {
                iArr[SettingsClockSize.DYNAMIC.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[SettingsClockSize.SMALL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KeyguardPreviewSmartspaceViewModel(Context context, KeyguardClockInteractor keyguardClockInteractor) {
        this.context = context;
        KeyguardClockRepository$special$$inlined$map$1 keyguardClockRepository$special$$inlined$map$1 = keyguardClockInteractor.selectedClockSize;
        this.smartspaceTopPadding = new KeyguardPreviewSmartspaceViewModel$special$$inlined$map$1(keyguardClockRepository$special$$inlined$map$1, this);
        this.shouldHideSmartspace = new KeyguardPreviewSmartspaceViewModel$special$$inlined$map$2(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(keyguardClockRepository$special$$inlined$map$1, keyguardClockInteractor.currentClockId, KeyguardPreviewSmartspaceViewModel$shouldHideSmartspace$2.INSTANCE));
    }
}
