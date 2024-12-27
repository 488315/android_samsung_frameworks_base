package com.android.systemui.unfold.util;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.provider.Settings;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import com.android.systemui.util.SettingsHelper;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;

public final class ScaleAwareTransitionProgressProvider implements UnfoldTransitionProgressProvider {
    public static final Companion Companion = new Companion(null);
    public final ContentResolver contentResolver;
    public final ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider;

    public final class Companion {
        private Companion() {
        }

        public static boolean areAnimationsEnabled(ContentResolver contentResolver) {
            Float floatOrNull;
            String string = Settings.Global.getString(contentResolver, SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE);
            return !(((string == null || (floatOrNull = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(string)) == null) ? 1.0f : floatOrNull.floatValue()) == 0.0f);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public ScaleAwareTransitionProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = new ScopedUnfoldTransitionProgressProvider(unfoldTransitionProgressProvider);
        this.scopedUnfoldTransitionProgressProvider = scopedUnfoldTransitionProgressProvider;
        contentResolver.registerContentObserver(Settings.Global.getUriFor(SettingsHelper.INDEX_GLOBAL_ANIMATOR_DURATION_SCALE), false, new ContentObserver() { // from class: com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider$animatorDurationScaleObserver$1
            {
                super(null);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                ScaleAwareTransitionProgressProvider scaleAwareTransitionProgressProvider = ScaleAwareTransitionProgressProvider.this;
                ScaleAwareTransitionProgressProvider.Companion companion = ScaleAwareTransitionProgressProvider.Companion;
                ContentResolver contentResolver2 = scaleAwareTransitionProgressProvider.contentResolver;
                ScaleAwareTransitionProgressProvider.Companion.getClass();
                scaleAwareTransitionProgressProvider.scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(contentResolver2));
            }
        });
        Companion.getClass();
        scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(Companion.areAnimationsEnabled(contentResolver));
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        this.scopedUnfoldTransitionProgressProvider.listeners.add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        this.scopedUnfoldTransitionProgressProvider.listeners.remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }
}
