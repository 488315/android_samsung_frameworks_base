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
import kotlin.KotlinNothingValueException;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class StatusBarPopupChipsViewModel extends ExclusiveActivatable {
    public final MutableState currentShownPopupChipId$delegate = SnapshotStateKt.mutableStateOf$default(null);
    public final Lazy mediaControlChip$delegate;
    public final State shownPopupChips$delegate;

    public interface Factory {
        StatusBarPopupChipsViewModel create();
    }

    public final class PopupChipBundle {
        public final PopupChipModel media;

        /* JADX WARN: Multi-variable type inference failed */
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

    /* renamed from: com.android.systemui.statusbar.featurepods.popups.ui.viewmodel.StatusBarPopupChipsViewModel$onActivated$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return StatusBarPopupChipsViewModel.this.onActivated(this);
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

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object onActivated(Continuation continuation) {
        AnonymousClass1 anonymousClass1;
        if (continuation instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuation;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuation);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            MediaControlChipViewModel mediaControlChipViewModel = (MediaControlChipViewModel) this.mediaControlChip$delegate.getValue();
            anonymousClass1.label = 1;
            if (mediaControlChipViewModel.activate(anonymousClass1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
