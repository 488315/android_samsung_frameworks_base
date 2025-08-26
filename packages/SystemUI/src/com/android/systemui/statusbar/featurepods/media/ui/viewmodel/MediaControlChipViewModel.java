package com.android.systemui.statusbar.featurepods.media.ui.viewmodel;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import androidx.compose.runtime.State;
import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.lifecycle.Hydrator;
import com.android.systemui.media.controls.shared.model.MediaAction;
import com.android.systemui.statusbar.featurepods.media.domain.interactor.MediaControlChipInteractor;
import com.android.systemui.statusbar.featurepods.media.shared.model.MediaControlChipModel;
import com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior;
import com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipId;
import com.android.systemui.statusbar.featurepods.popups.shared.model.PopupChipModel;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class MediaControlChipViewModel extends ExclusiveActivatable {
    public final Context applicationContext;
    public final State chip$delegate;
    public final Hydrator hydrator;

    public interface Factory {
        MediaControlChipViewModel create();
    }

    /* renamed from: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$onActivated$1, reason: invalid class name */
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
            return MediaControlChipViewModel.this.onActivated(this);
        }
    }

    public MediaControlChipViewModel(Context context, MediaControlChipInteractor mediaControlChipInteractor) {
        this.applicationContext = context;
        Hydrator hydrator = new Hydrator("MediaControlChipViewModel.hydrator", null, 2, null);
        this.hydrator = hydrator;
        PopupChipModel.Hidden hidden = new PopupChipModel.Hidden(PopupChipId.MediaControl.INSTANCE, false, 2, null);
        final ReadonlyStateFlow readonlyStateFlow = mediaControlChipInteractor.mediaControlChipModel;
        this.chip$delegate = hydrator.hydratedStateOf("chip", hidden, new Flow() { // from class: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1

            /* renamed from: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaControlChipViewModel this$0;

                /* renamed from: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, MediaControlChipViewModel mediaControlChipViewModel) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaControlChipViewModel;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object hidden;
                    CharSequence charSequence;
                    Drawable drawable;
                    final Runnable runnable;
                    Drawable drawableLoadDrawable;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        MediaControlChipModel mediaControlChipModel = (MediaControlChipModel) obj;
                        MediaControlChipViewModel mediaControlChipViewModel = this.this$0;
                        mediaControlChipViewModel.getClass();
                        if (mediaControlChipModel == null || (charSequence = mediaControlChipModel.songName) == null || charSequence.length() == 0) {
                            hidden = new PopupChipModel.Hidden(PopupChipId.MediaControl.INSTANCE, false, 2, null);
                        } else {
                            String str = mediaControlChipModel.appName;
                            ContentDescription.Loaded loaded = str != null ? new ContentDescription.Loaded(str) : null;
                            Icon icon = mediaControlChipModel.appIcon;
                            com.android.systemui.common.shared.model.Icon resource = (icon == null || (drawableLoadDrawable = icon.loadDrawable(mediaControlChipViewModel.applicationContext)) == null) ? new Icon.Resource(R.drawable.ic_dialog_close_normal_holo, loaded) : new Icon.Loaded(drawableLoadDrawable, loaded, null, 4, null);
                            PopupChipId.MediaControl mediaControl = PopupChipId.MediaControl.INSTANCE;
                            String string = mediaControlChipModel.songName.toString();
                            MediaAction mediaAction = mediaControlChipModel.playOrPause;
                            HoverBehavior button = (mediaAction == null || (drawable = mediaAction.icon) == null || (runnable = mediaAction.action) == null) ? HoverBehavior.None.INSTANCE : new HoverBehavior.Button(new Icon.Loaded(drawable, new ContentDescription.Loaded(String.valueOf(mediaAction.contentDescription)), null, 4, null), 
                            /*  JADX ERROR: Method code generation error
                                jadx.core.utils.exceptions.CodegenException: Error generate insn: ?: TERNARY (r1v8 'button' com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior) = (((r1v5 'mediaAction' com.android.systemui.media.controls.shared.model.MediaAction) == (null com.android.systemui.media.controls.shared.model.MediaAction) || (wrap:android.graphics.drawable.Drawable:0x008b: IGET (r4v5 'drawable' android.graphics.drawable.Drawable) = (r1v5 'mediaAction' com.android.systemui.media.controls.shared.model.MediaAction) A[FORCE_ASSIGN_INLINE, WRAPPED] (LINE:140) com.android.systemui.media.controls.shared.model.MediaAction.icon android.graphics.drawable.Drawable) == (null android.graphics.drawable.Drawable) || (wrap:java.lang.Runnable:0x0092: IGET (r6v3 'runnable' java.lang.Runnable) = (r1v5 'mediaAction' com.android.systemui.media.controls.shared.model.MediaAction) A[FORCE_ASSIGN_INLINE, WRAPPED] (LINE:147) com.android.systemui.media.controls.shared.model.MediaAction.action java.lang.Runnable) == (null java.lang.Runnable))) ? (wrap:com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior:0x0096: SGET  A[WRAPPED] (LINE:151) com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior.None.INSTANCE com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior$None) : (wrap:com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior:0x00ba: CONSTRUCTOR (r1v8 'button' com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior) = 
                                  (wrap:com.android.systemui.common.shared.model.Icon$Loaded:0x00b2: CONSTRUCTOR 
                                  (r4v5 'drawable' android.graphics.drawable.Drawable)
                                  (wrap:com.android.systemui.common.shared.model.ContentDescription$Loaded:0x00a1: CONSTRUCTOR 
                                  (wrap:java.lang.String:0x009d: INVOKE 
                                  (wrap:java.lang.CharSequence:0x009b: IGET (r1v5 'mediaAction' com.android.systemui.media.controls.shared.model.MediaAction) A[WRAPPED] (LINE:156) com.android.systemui.media.controls.shared.model.MediaAction.contentDescription java.lang.CharSequence)
                                 STATIC call: java.lang.String.valueOf(java.lang.Object):java.lang.String A[MD:(java.lang.Object):java.lang.String (c), WRAPPED] (LINE:158))
                                 A[MD:(java.lang.String):void (m), WRAPPED] (LINE:162) call: com.android.systemui.common.shared.model.ContentDescription.Loaded.<init>(java.lang.String):void type: CONSTRUCTOR)
                                  (null java.lang.Integer)
                                  (4 int)
                                  (null kotlin.jvm.internal.DefaultConstructorMarker)
                                 A[MD:(android.graphics.drawable.Drawable, com.android.systemui.common.shared.model.ContentDescription, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void (m), WRAPPED] (LINE:179) call: com.android.systemui.common.shared.model.Icon.Loaded.<init>(android.graphics.drawable.Drawable, com.android.systemui.common.shared.model.ContentDescription, java.lang.Integer, int, kotlin.jvm.internal.DefaultConstructorMarker):void type: CONSTRUCTOR)
                                  (wrap:kotlin.jvm.functions.Function0:0x00b7: CONSTRUCTOR (r6v3 'runnable' java.lang.Runnable A[DONT_INLINE]) A[MD:(java.lang.Runnable):void (m), WRAPPED] (LINE:184) call: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$$ExternalSyntheticLambda0.<init>(java.lang.Runnable):void type: CONSTRUCTOR)
                                 A[MD:(com.android.systemui.common.shared.model.Icon, kotlin.jvm.functions.Function0):void (m), WRAPPED] (LINE:187) call: com.android.systemui.statusbar.featurepods.popups.shared.model.HoverBehavior.Button.<init>(com.android.systemui.common.shared.model.Icon, kotlin.jvm.functions.Function0):void type: CONSTRUCTOR) in method: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1.2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object, file: classes3.dex
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                                	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                                	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:140)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                                	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                                	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.dex.regions.Region.generate(Region.java:35)
                                	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                                	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:298)
                                	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:277)
                                	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:410)
                                	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                                	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                                	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
                                	at java.base/java.util.ArrayList.forEach(ArrayList.java:1596)
                                	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                                	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
                                Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$$ExternalSyntheticLambda0, state: NOT_LOADED
                                	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:304)
                                	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                                	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:782)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                                	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                                	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                                	at jadx.core.codegen.InsnGen.makeTernary(InsnGen.java:1189)
                                	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:536)
                                	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                                	... 27 more
                                */
                            /*
                                Method dump skipped, instructions count: 226
                                To view this dump add '--comments-level debug' option
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.featurepods.media.ui.viewmodel.MediaControlChipViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                        }
                    }

                    @Override // kotlinx.coroutines.flow.Flow
                    public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                        Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                });
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
                    anonymousClass1.label = 1;
                    if (this.hydrator.activate(anonymousClass1) == coroutineSingletons) {
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
