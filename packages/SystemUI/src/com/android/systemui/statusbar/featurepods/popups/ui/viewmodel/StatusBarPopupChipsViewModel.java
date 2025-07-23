package com.android.systemui.statusbar.featurepods.popups.ui.viewmodel;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel;
import com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipId;
import com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel;
import com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class StatusBarPopupChipsViewModel extends ExclusiveActivatable {
    public final MutableState currentShownPopupChipId$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final Lazy mediaControlChip$delegate;
    public final State shownPopupChips$delegate;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        StatusBarPopupChipsViewModel create();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class PopupChipBundle {
        public final PopupChipModel media;

        public PopupChipBundle() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof PopupChipBundle) && Intrinsics.areEqual(this.media, ((PopupChipBundle) obj).media);
        }

        public final int hashCode() {
            return this.media.hashCode();
        }

        public final String toString() {
            return "PopupChipBundle(media=" + this.media + ")";
        }

        public PopupChipBundle(PopupChipModel popupChipModel) {
            this.media = popupChipModel;
        }

        public /* synthetic */ PopupChipBundle(PopupChipModel popupChipModel, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? new PopupChipModel.Hidden(PopupChipId.MediaControl.INSTANCE, false, 2, null) : popupChipModel);
        }
    }

    public StatusBarPopupChipsViewModel(final MediaControlChipViewModel.Factory factory) {
        final int i = 0;
        this.mediaControlChip$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        return ((MediaControlChipViewModel.Factory) factory).create();
                    default:
                        return new StatusBarPopupChipsViewModel.PopupChipBundle((PopupChipModel) ((SnapshotMutableStateImpl) ((MediaControlChipViewModel) ((StatusBarPopupChipsViewModel) factory).mediaControlChip$delegate.getValue()).chip$delegate).getValue());
                }
            }
        });
        final int i2 = 1;
        SnapshotStateKt.derivedStateOf(new Function0() { // from class: com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        return ((MediaControlChipViewModel.Factory) this).create();
                    default:
                        return new StatusBarPopupChipsViewModel.PopupChipBundle((PopupChipModel) ((SnapshotMutableStateImpl) ((MediaControlChipViewModel) ((StatusBarPopupChipsViewModel) this).mediaControlChip$delegate.getValue()).chip$delegate).getValue());
                }
            }
        });
        this.shownPopupChips$delegate = SnapshotStateKt.derivedStateOf(new StatusBarPopupChipsViewModel$$ExternalSyntheticLambda2());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel$onActivated$1 r0 = (com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel$onActivated$1 r0 = new com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L43
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlin.Lazy r4 = r4.mediaControlChip$delegate
            java.lang.Object r4 = r4.getValue()
            com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel r4 = (com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel) r4
            r0.label = r3
            java.lang.Object r4 = r4.activate(r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }
}
