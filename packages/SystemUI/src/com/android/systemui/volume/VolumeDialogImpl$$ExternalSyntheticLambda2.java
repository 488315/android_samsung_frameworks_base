package com.android.systemui.volume;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.android.settingslib.Utils;
import com.android.systemui.Prefs;
import com.android.systemui.R;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.volume.VolumeDialogImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class VolumeDialogImpl$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VolumeDialogImpl f$0;

    public /* synthetic */ VolumeDialogImpl$$ExternalSyntheticLambda2(VolumeDialogImpl volumeDialogImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = volumeDialogImpl;
    }

    @Override // java.lang.Runnable
    public final void run() {
        VolumeDialogImpl.CustomDialog customDialog;
        final ImageButton imageButton;
        int i = this.$r8$classId;
        final VolumeDialogImpl volumeDialogImpl = this.f$0;
        switch (i) {
            case 0:
                volumeDialogImpl.getDrawerIconViewForMode(volumeDialogImpl.mState.ringerModeInternal).setVisibility(0);
                return;
            case 1:
                View view = volumeDialogImpl.mODICaptionsTooltipView;
                if (view != null) {
                    view.setVisibility(4);
                    return;
                }
                return;
            case 2:
                VolumeDialogController volumeDialogController = volumeDialogImpl.mController;
                if (volumeDialogController != null) {
                    volumeDialogController.notifyVisible(false);
                }
                VolumeDialogImpl.CustomDialog customDialog2 = volumeDialogImpl.mDialog;
                if (customDialog2 != null) {
                    customDialog2.dismiss();
                }
                if (volumeDialogImpl.mHasSeenODICaptionsTooltip && volumeDialogImpl.mODICaptionsTooltipView != null && (customDialog = volumeDialogImpl.mDialog) != null) {
                    ((ViewGroup) customDialog.findViewById(R.id.volume_dialog_container)).removeView(volumeDialogImpl.mODICaptionsTooltipView);
                    volumeDialogImpl.mODICaptionsTooltipView = null;
                }
                volumeDialogImpl.mIsAnimatingDismiss = false;
                volumeDialogImpl.hideRingerDrawer();
                return;
            case 3:
                if (Prefs.getBoolean(volumeDialogImpl.mContext, "TouchedRingerToggle", false) || (imageButton = volumeDialogImpl.mRingerIcon) == null) {
                    return;
                }
                imageButton.postOnAnimationDelayed(new Runnable() { // from class: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda18
                    @Override // java.lang.Runnable
                    public final void run() {
                        VolumeDialogImpl volumeDialogImpl2 = VolumeDialogImpl.this;
                        ImageButton imageButton2 = imageButton;
                        volumeDialogImpl2.getClass();
                        if (imageButton2 != null) {
                            imageButton2.setPressed(true);
                            imageButton2.postOnAnimationDelayed(new VolumeDialogImpl$$ExternalSyntheticLambda21(imageButton2, 0), 200L);
                        }
                    }
                }, 1500L);
                return;
            case 4:
                LayerDrawable layerDrawable = (LayerDrawable) volumeDialogImpl.mRingerAndDrawerContainer.getBackground();
                if (layerDrawable == null || layerDrawable.getNumberOfLayers() <= 0) {
                    return;
                }
                volumeDialogImpl.mRingerAndDrawerContainerBackground = layerDrawable.getDrawable(0);
                volumeDialogImpl.updateBackgroundForDrawerClosedAmount();
                if (volumeDialogImpl.mTopContainer == null) {
                    return;
                }
                LayerDrawable layerDrawable2 = new LayerDrawable(new Drawable[]{new ColorDrawable(Utils.getColorAttrDefaultColor(volumeDialogImpl.mContext, android.R.^attr-private.colorSurface, 0))});
                layerDrawable2.setLayerSize(0, volumeDialogImpl.mDialogWidth, !volumeDialogImpl.isLandscape() ? volumeDialogImpl.mDialogRowsView.getHeight() : volumeDialogImpl.mDialogRowsView.getHeight() + volumeDialogImpl.mDialogCornerRadius);
                layerDrawable2.setLayerInsetTop(0, !volumeDialogImpl.isLandscape() ? volumeDialogImpl.mDialogRowsViewContainer.getTop() : volumeDialogImpl.mDialogRowsViewContainer.getTop() - volumeDialogImpl.mDialogCornerRadius);
                layerDrawable2.setLayerGravity(0, 53);
                if (volumeDialogImpl.isLandscape()) {
                    volumeDialogImpl.mRingerAndDrawerContainer.setOutlineProvider(
                    /*  JADX ERROR: Method code generation error
                        jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x00a7: INVOKE 
                          (wrap:android.view.View:0x00a0: IGET (r6v1 'volumeDialogImpl' com.android.systemui.volume.VolumeDialogImpl) A[WRAPPED] (LINE:161) com.android.systemui.volume.VolumeDialogImpl.mRingerAndDrawerContainer android.view.View)
                          (wrap:android.view.ViewOutlineProvider:0x00a4: CONSTRUCTOR (r6v1 'volumeDialogImpl' com.android.systemui.volume.VolumeDialogImpl A[DONT_INLINE]) A[MD:(com.android.systemui.volume.VolumeDialogImpl):void (m), WRAPPED] (LINE:165) call: com.android.systemui.volume.VolumeDialogImpl.6.<init>(com.android.systemui.volume.VolumeDialogImpl):void type: CONSTRUCTOR)
                         VIRTUAL call: android.view.View.setOutlineProvider(android.view.ViewOutlineProvider):void A[MD:(android.view.ViewOutlineProvider):void (c)] (LINE:168) in method: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda2.run():void, file: classes3.dex
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
                        	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:126)
                        	at jadx.core.dex.regions.conditions.IfRegion.generate(IfRegion.java:90)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:83)
                        	at jadx.core.codegen.RegionGen.makeSwitch(RegionGen.java:267)
                        	at jadx.core.dex.regions.SwitchRegion.generate(SwitchRegion.java:84)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.dex.regions.Region.generate(Region.java:35)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        Caused by: jadx.core.utils.exceptions.JadxRuntimeException: Expected class to be processed at this point, class: com.android.systemui.volume.VolumeDialogImpl, state: NOT_LOADED
                        	at jadx.core.dex.nodes.ClassNode.ensureProcessed(ClassNode.java:305)
                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:807)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
                        	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
                        	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
                        	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
                        	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
                        	... 35 more
                        */
                    /*
                        Method dump skipped, instructions count: 298
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.VolumeDialogImpl$$ExternalSyntheticLambda2.run():void");
                }
            }
