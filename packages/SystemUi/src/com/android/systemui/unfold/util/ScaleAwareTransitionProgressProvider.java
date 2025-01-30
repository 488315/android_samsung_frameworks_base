package com.android.systemui.unfold.util;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.provider.Settings;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScaleAwareTransitionProgressProvider implements UnfoldTransitionProgressProvider {
    public static final Companion Companion = new Companion(null);
    public final ContentResolver contentResolver;
    public final ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean areAnimationsEnabled(ContentResolver contentResolver) {
            Float floatOrNull;
            String string = Settings.Global.getString(contentResolver, "animator_duration_scale");
            return !(((string == null || (floatOrNull = StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(string)) == null) ? 1.0f : floatOrNull.floatValue()) == 0.0f);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {
        ScaleAwareTransitionProgressProvider wrap(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider);
    }

    public ScaleAwareTransitionProgressProvider(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
        ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider = new ScopedUnfoldTransitionProgressProvider(unfoldTransitionProgressProvider);
        this.scopedUnfoldTransitionProgressProvider = scopedUnfoldTransitionProgressProvider;
        contentResolver.registerContentObserver(Settings.Global.getUriFor("animator_duration_scale"), false, new ContentObserver() { // from class: com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider$animatorDurationScaleObserver$1
            {
                super(null);
            }

            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                ScaleAwareTransitionProgressProvider scaleAwareTransitionProgressProvider = ScaleAwareTransitionProgressProvider.this;
                ScaleAwareTransitionProgressProvider.Companion companion = ScaleAwareTransitionProgressProvider.Companion;
                scaleAwareTransitionProgressProvider.getClass();
                ScaleAwareTransitionProgressProvider.Companion.getClass();
                scaleAwareTransitionProgressProvider.scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(ScaleAwareTransitionProgressProvider.Companion.areAnimationsEnabled(scaleAwareTransitionProgressProvider.contentResolver));
            }
        });
        Companion.getClass();
        scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(Companion.areAnimationsEnabled(contentResolver));
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void addCallback(Object obj) {
        ((ArrayList) this.scopedUnfoldTransitionProgressProvider.listeners).add((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }

    @Override // com.android.systemui.unfold.util.CallbackController
    public final void removeCallback(Object obj) {
        ((ArrayList) this.scopedUnfoldTransitionProgressProvider.listeners).remove((UnfoldTransitionProgressProvider.TransitionProgressListener) obj);
    }
}
