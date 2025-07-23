package android.view.inputmethod;

import android.annotation.NonNull;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Size;
import android.util.Slog;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InlineSuggestion;
import android.widget.inline.InlineContentView;
import com.android.internal.util.AnnotationValidations;
import com.android.internal.util.Parcelling;
import com.android.internal.view.inline.IInlineContentCallback;
import com.android.internal.view.inline.IInlineContentProvider;
import com.android.internal.view.inline.InlineTooltipUi;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public final class InlineSuggestion implements Parcelable {
    public static final Parcelable.Creator<InlineSuggestion> CREATOR;
    private static final String TAG = "InlineSuggestion";
    static Parcelling<InlineContentCallbackImpl> sParcellingForInlineContentCallback;
    static Parcelling<InlineTooltipUi> sParcellingForInlineTooltipUi;
    private final IInlineContentProvider mContentProvider;
    private final InlineSuggestionInfo mInfo;
    private InlineContentCallbackImpl mInlineContentCallback;
    private InlineTooltipUi mInlineTooltipUi;

    @Deprecated
    private void __metadata() {
    }

    private static boolean isValid(int i, int i2, int i3) {
        if (i == -2) {
            return true;
        }
        return i >= i2 && i <= i3;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static InlineSuggestion newInlineSuggestion(InlineSuggestionInfo inlineSuggestionInfo) {
        return new InlineSuggestion(inlineSuggestionInfo, null, null, null);
    }

    public InlineSuggestion(InlineSuggestionInfo inlineSuggestionInfo, IInlineContentProvider iInlineContentProvider) {
        this(inlineSuggestionInfo, iInlineContentProvider, null, null);
    }

    public void inflate(Context context, Size size, Executor executor, final Consumer<InlineContentView> consumer) {
        Size minSize = this.mInfo.getInlinePresentationSpec().getMinSize();
        Size maxSize = this.mInfo.getInlinePresentationSpec().getMaxSize();
        if (!isValid(size.getWidth(), minSize.getWidth(), maxSize.getWidth()) || !isValid(size.getHeight(), minSize.getHeight(), maxSize.getHeight())) {
            throw new IllegalArgumentException("size is neither between min:" + minSize + " and max:" + maxSize + ", nor wrap_content");
        }
        InlineSuggestion tooltip = this.mInfo.getTooltip();
        if (tooltip != null) {
            if (this.mInlineTooltipUi == null) {
                this.mInlineTooltipUi = new InlineTooltipUi(context);
            }
        } else {
            this.mInlineTooltipUi = null;
        }
        this.mInlineContentCallback = getInlineContentCallback(context, executor, consumer, this.mInlineTooltipUi);
        IInlineContentProvider iInlineContentProvider = this.mContentProvider;
        if (iInlineContentProvider == null) {
            executor.execute(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(null);
                }
            });
            this.mInlineTooltipUi = null;
            return;
        }
        try {
            iInlineContentProvider.provideContent(size.getWidth(), size.getHeight(), new InlineContentCallbackWrapper(this.mInlineContentCallback));
        } catch (RemoteException e) {
            Slog.w(TAG, "Error creating suggestion content surface: " + e);
            executor.execute(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    consumer.accept(null);
                }
            });
        }
        if (tooltip == null) {
            return;
        }
        this.mInfo.getTooltip().inflate(context, new Size(-2, -2), executor, new Consumer() { // from class: android.view.inputmethod.InlineSuggestion$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                InlineSuggestion.this.lambda$inflate$3((InlineContentView) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflate$2(InlineContentView inlineContentView) {
        this.mInlineTooltipUi.setTooltipView(inlineContentView);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$inflate$3(final InlineContentView inlineContentView) {
        Handler.getMain().post(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                InlineSuggestion.this.lambda$inflate$2(inlineContentView);
            }
        });
    }

    private synchronized InlineContentCallbackImpl getInlineContentCallback(Context context, Executor executor, Consumer<InlineContentView> consumer, InlineTooltipUi inlineTooltipUi) {
        if (this.mInlineContentCallback != null) {
            throw new IllegalStateException("Already called #inflate()");
        }
        return new InlineContentCallbackImpl(context, this.mContentProvider, executor, consumer, inlineTooltipUi);
    }

    private static final class InlineContentCallbackWrapper extends IInlineContentCallback.Stub {
        private final WeakReference<InlineContentCallbackImpl> mCallbackImpl;

        InlineContentCallbackWrapper(InlineContentCallbackImpl inlineContentCallbackImpl) {
            this.mCallbackImpl = new WeakReference<>(inlineContentCallbackImpl);
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onContent(SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) {
            InlineContentCallbackImpl inlineContentCallbackImpl = this.mCallbackImpl.get();
            if (inlineContentCallbackImpl != null) {
                inlineContentCallbackImpl.onContent(surfacePackage, i, i2);
            }
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onClick() {
            InlineContentCallbackImpl inlineContentCallbackImpl = this.mCallbackImpl.get();
            if (inlineContentCallbackImpl != null) {
                inlineContentCallbackImpl.onClick();
            }
        }

        @Override // com.android.internal.view.inline.IInlineContentCallback
        public void onLongClick() {
            InlineContentCallbackImpl inlineContentCallbackImpl = this.mCallbackImpl.get();
            if (inlineContentCallbackImpl != null) {
                inlineContentCallbackImpl.onLongClick();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static final class InlineContentCallbackImpl {
        private final Consumer<InlineContentView> mCallback;
        private final Executor mCallbackExecutor;
        private final Context mContext;
        private final IInlineContentProvider mInlineContentProvider;
        private InlineTooltipUi mInlineTooltipUi;
        private SurfaceControlViewHost.SurfacePackage mSurfacePackage;
        private Consumer<SurfaceControlViewHost.SurfacePackage> mSurfacePackageConsumer;
        private InlineContentView mView;
        private final Handler mMainHandler = new Handler(Looper.getMainLooper());
        private boolean mFirstContentReceived = false;

        InlineContentCallbackImpl(Context context, IInlineContentProvider iInlineContentProvider, Executor executor, Consumer<InlineContentView> consumer, InlineTooltipUi inlineTooltipUi) {
            this.mContext = context;
            this.mInlineContentProvider = iInlineContentProvider;
            this.mCallbackExecutor = executor;
            this.mCallback = consumer;
            this.mInlineTooltipUi = inlineTooltipUi;
        }

        public void onContent(final SurfaceControlViewHost.SurfacePackage surfacePackage, final int i, final int i2) {
            this.mMainHandler.post(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    InlineSuggestion.InlineContentCallbackImpl.this.lambda$onContent$0(surfacePackage, i, i2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: handleOnContent, reason: merged with bridge method [inline-methods] */
        public void lambda$onContent$0(SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) {
            if (!this.mFirstContentReceived) {
                handleOnFirstContentReceived(surfacePackage, i, i2);
                this.mFirstContentReceived = true;
            } else {
                handleOnSurfacePackage(surfacePackage);
            }
        }

        private void handleOnFirstContentReceived(SurfaceControlViewHost.SurfacePackage surfacePackage, int i, int i2) {
            this.mSurfacePackage = surfacePackage;
            if (surfacePackage == null) {
                this.mCallbackExecutor.execute(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        InlineSuggestion.InlineContentCallbackImpl.this.lambda$handleOnFirstContentReceived$1();
                    }
                });
                return;
            }
            InlineContentView inlineContentView = new InlineContentView(this.mContext);
            this.mView = inlineContentView;
            if (this.mInlineTooltipUi != null) {
                inlineContentView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: android.view.inputmethod.InlineSuggestion.InlineContentCallbackImpl.1
                    @Override // android.view.View.OnLayoutChangeListener
                    public void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                        if (InlineContentCallbackImpl.this.mInlineTooltipUi != null) {
                            InlineContentCallbackImpl.this.mInlineTooltipUi.update(InlineContentCallbackImpl.this.mView);
                        }
                    }
                });
            }
            this.mView.setLayoutParams(new ViewGroup.LayoutParams(i, i2));
            this.mView.setChildSurfacePackageUpdater(getSurfacePackageUpdater());
            this.mCallbackExecutor.execute(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    InlineSuggestion.InlineContentCallbackImpl.this.lambda$handleOnFirstContentReceived$2();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleOnFirstContentReceived$1() {
            this.mCallback.accept(null);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$handleOnFirstContentReceived$2() {
            this.mCallback.accept(this.mView);
        }

        private void handleOnSurfacePackage(SurfaceControlViewHost.SurfacePackage surfacePackage) {
            Consumer<SurfaceControlViewHost.SurfacePackage> consumer;
            if (surfacePackage == null) {
                return;
            }
            if (this.mSurfacePackage != null || (consumer = this.mSurfacePackageConsumer) == null) {
                surfacePackage.release();
                try {
                    this.mInlineContentProvider.onSurfacePackageReleased();
                    return;
                } catch (RemoteException e) {
                    Slog.w(InlineSuggestion.TAG, "Error calling onSurfacePackageReleased(): " + e);
                    return;
                }
            }
            this.mSurfacePackage = surfacePackage;
            if (surfacePackage == null || consumer == null) {
                return;
            }
            consumer.accept(surfacePackage);
            this.mSurfacePackageConsumer = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleOnSurfacePackageReleased() {
            if (this.mSurfacePackage != null) {
                try {
                    this.mInlineContentProvider.onSurfacePackageReleased();
                } catch (RemoteException e) {
                    Slog.w(InlineSuggestion.TAG, "Error calling onSurfacePackageReleased(): " + e);
                }
                this.mSurfacePackage = null;
            }
            this.mSurfacePackageConsumer = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void handleGetSurfacePackage(Consumer<SurfaceControlViewHost.SurfacePackage> consumer) {
            SurfaceControlViewHost.SurfacePackage surfacePackage = this.mSurfacePackage;
            if (surfacePackage != null) {
                consumer.accept(surfacePackage);
                return;
            }
            this.mSurfacePackageConsumer = consumer;
            try {
                this.mInlineContentProvider.requestSurfacePackage();
            } catch (RemoteException e) {
                Slog.w(InlineSuggestion.TAG, "Error calling getSurfacePackage(): " + e);
                consumer.accept(null);
                this.mSurfacePackageConsumer = null;
            }
        }

        /* renamed from: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$2, reason: invalid class name */
        class AnonymousClass2 implements InlineContentView.SurfacePackageUpdater {
            AnonymousClass2() {
            }

            @Override // android.widget.inline.InlineContentView.SurfacePackageUpdater
            public void onSurfacePackageReleased() {
                InlineContentCallbackImpl.this.mMainHandler.post(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        InlineSuggestion.InlineContentCallbackImpl.AnonymousClass2.this.lambda$onSurfacePackageReleased$0();
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$onSurfacePackageReleased$0() {
                InlineContentCallbackImpl.this.handleOnSurfacePackageReleased();
            }

            @Override // android.widget.inline.InlineContentView.SurfacePackageUpdater
            public void getSurfacePackage(final Consumer<SurfaceControlViewHost.SurfacePackage> consumer) {
                InlineContentCallbackImpl.this.mMainHandler.post(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        InlineSuggestion.InlineContentCallbackImpl.AnonymousClass2.this.lambda$getSurfacePackage$1(consumer);
                    }
                });
            }

            /* JADX INFO: Access modifiers changed from: private */
            public /* synthetic */ void lambda$getSurfacePackage$1(Consumer consumer) {
                InlineContentCallbackImpl.this.handleGetSurfacePackage(consumer);
            }
        }

        private InlineContentView.SurfacePackageUpdater getSurfacePackageUpdater() {
            return new AnonymousClass2();
        }

        public void onClick() {
            this.mMainHandler.post(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    InlineSuggestion.InlineContentCallbackImpl.this.lambda$onClick$3();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClick$3() {
            InlineContentView inlineContentView = this.mView;
            if (inlineContentView == null || !inlineContentView.hasOnClickListeners()) {
                return;
            }
            this.mView.callOnClick();
        }

        public void onLongClick() {
            this.mMainHandler.post(new Runnable() { // from class: android.view.inputmethod.InlineSuggestion$InlineContentCallbackImpl$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    InlineSuggestion.InlineContentCallbackImpl.this.lambda$onLongClick$4();
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onLongClick$4() {
            InlineContentView inlineContentView = this.mView;
            if (inlineContentView == null || !inlineContentView.hasOnLongClickListeners()) {
                return;
            }
            this.mView.performLongClick();
        }
    }

    private static class InlineContentCallbackImplParceling implements Parcelling<InlineContentCallbackImpl> {
        @Override // com.android.internal.util.Parcelling
        public void parcel(InlineContentCallbackImpl inlineContentCallbackImpl, Parcel parcel, int i) {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.util.Parcelling
        public InlineContentCallbackImpl unparcel(Parcel parcel) {
            return null;
        }

        private InlineContentCallbackImplParceling() {
        }
    }

    private static class InlineTooltipUiParceling implements Parcelling<InlineTooltipUi> {
        @Override // com.android.internal.util.Parcelling
        public void parcel(InlineTooltipUi inlineTooltipUi, Parcel parcel, int i) {
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.android.internal.util.Parcelling
        public InlineTooltipUi unparcel(Parcel parcel) {
            return null;
        }

        private InlineTooltipUiParceling() {
        }
    }

    public InlineSuggestion(InlineSuggestionInfo inlineSuggestionInfo, IInlineContentProvider iInlineContentProvider, InlineContentCallbackImpl inlineContentCallbackImpl, InlineTooltipUi inlineTooltipUi) {
        this.mInfo = inlineSuggestionInfo;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) inlineSuggestionInfo);
        this.mContentProvider = iInlineContentProvider;
        this.mInlineContentCallback = inlineContentCallbackImpl;
        this.mInlineTooltipUi = inlineTooltipUi;
    }

    public InlineSuggestionInfo getInfo() {
        return this.mInfo;
    }

    public IInlineContentProvider getContentProvider() {
        return this.mContentProvider;
    }

    public InlineContentCallbackImpl getInlineContentCallback() {
        return this.mInlineContentCallback;
    }

    public InlineTooltipUi getInlineTooltipUi() {
        return this.mInlineTooltipUi;
    }

    public String toString() {
        return "InlineSuggestion { info = " + this.mInfo + ", contentProvider = " + this.mContentProvider + ", inlineContentCallback = " + this.mInlineContentCallback + ", inlineTooltipUi = " + this.mInlineTooltipUi + " }";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            InlineSuggestion inlineSuggestion = (InlineSuggestion) obj;
            if (Objects.equals(this.mInfo, inlineSuggestion.mInfo) && Objects.equals(this.mContentProvider, inlineSuggestion.mContentProvider) && Objects.equals(this.mInlineContentCallback, inlineSuggestion.mInlineContentCallback) && Objects.equals(this.mInlineTooltipUi, inlineSuggestion.mInlineTooltipUi)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((((Objects.hashCode(this.mInfo) + 31) * 31) + Objects.hashCode(this.mContentProvider)) * 31) + Objects.hashCode(this.mInlineContentCallback)) * 31) + Objects.hashCode(this.mInlineTooltipUi);
    }

    static {
        Parcelling<InlineContentCallbackImpl> parcelling = Parcelling.Cache.get(InlineContentCallbackImplParceling.class);
        sParcellingForInlineContentCallback = parcelling;
        if (parcelling == null) {
            sParcellingForInlineContentCallback = Parcelling.Cache.put(new InlineContentCallbackImplParceling());
        }
        Parcelling<InlineTooltipUi> parcelling2 = Parcelling.Cache.get(InlineTooltipUiParceling.class);
        sParcellingForInlineTooltipUi = parcelling2;
        if (parcelling2 == null) {
            sParcellingForInlineTooltipUi = Parcelling.Cache.put(new InlineTooltipUiParceling());
        }
        CREATOR = new Parcelable.Creator<InlineSuggestion>() { // from class: android.view.inputmethod.InlineSuggestion.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InlineSuggestion[] newArray(int i) {
                return new InlineSuggestion[i];
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public InlineSuggestion createFromParcel(Parcel parcel) {
                return new InlineSuggestion(parcel);
            }
        };
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mContentProvider != null ? (byte) 2 : (byte) 0;
        if (this.mInlineContentCallback != null) {
            b = (byte) (b | 4);
        }
        if (this.mInlineTooltipUi != null) {
            b = (byte) (b | 8);
        }
        parcel.writeByte(b);
        parcel.writeTypedObject(this.mInfo, i);
        IInlineContentProvider iInlineContentProvider = this.mContentProvider;
        if (iInlineContentProvider != null) {
            parcel.writeStrongInterface(iInlineContentProvider);
        }
        sParcellingForInlineContentCallback.parcel(this.mInlineContentCallback, parcel, i);
        sParcellingForInlineTooltipUi.parcel(this.mInlineTooltipUi, parcel, i);
    }

    InlineSuggestion(Parcel parcel) {
        byte readByte = parcel.readByte();
        InlineSuggestionInfo inlineSuggestionInfo = (InlineSuggestionInfo) parcel.readTypedObject(InlineSuggestionInfo.CREATOR);
        IInlineContentProvider asInterface = (readByte & 2) == 0 ? null : IInlineContentProvider.Stub.asInterface(parcel.readStrongBinder());
        InlineContentCallbackImpl unparcel = sParcellingForInlineContentCallback.unparcel(parcel);
        InlineTooltipUi unparcel2 = sParcellingForInlineTooltipUi.unparcel(parcel);
        this.mInfo = inlineSuggestionInfo;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) inlineSuggestionInfo);
        this.mContentProvider = asInterface;
        this.mInlineContentCallback = unparcel;
        this.mInlineTooltipUi = unparcel2;
    }
}
