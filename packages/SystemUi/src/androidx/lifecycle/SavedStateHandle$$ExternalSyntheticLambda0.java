package androidx.lifecycle;

import android.os.Bundle;
import androidx.savedstate.SavedStateRegistry;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SavedStateHandle$$ExternalSyntheticLambda0 implements SavedStateRegistry.SavedStateProvider {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SavedStateHandle f$0;

    public /* synthetic */ SavedStateHandle$$ExternalSyntheticLambda0(SavedStateHandle savedStateHandle, int i) {
        this.$r8$classId = i;
        this.f$0 = savedStateHandle;
    }

    @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
    public final Bundle saveState() {
        int i = this.$r8$classId;
        SavedStateHandle savedStateHandle = this.f$0;
        switch (i) {
        }
        return SavedStateHandle.$r8$lambda$aMir0GWwzPQviKVGE0DPm0kayew(savedStateHandle);
    }
}
