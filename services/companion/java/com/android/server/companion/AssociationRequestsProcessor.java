package com.android.server.companion;

import android.R;
import android.app.PendingIntent;
import android.companion.AssociatedDevice;
import android.companion.AssociationInfo;
import android.companion.AssociationRequest;
import android.companion.IAssociationRequestCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManagerInternal;
import android.content.pm.PackageManager;
import android.net.MacAddress;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.clipboard.ClipboardService;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class AssociationRequestsProcessor {
    public static final ComponentName ASSOCIATION_REQUEST_APPROVAL_ACTIVITY = ComponentName.createRelative("com.android.companiondevicemanager", ".CompanionDeviceActivity");
    public final AssociationStoreImpl mAssociationStore;
    public final Context mContext;
    public final ResultReceiver mOnRequestConfirmationReceiver = new ResultReceiver(Handler.getMain()) { // from class: com.android.server.companion.AssociationRequestsProcessor.1
        @Override // android.os.ResultReceiver
        public void onReceiveResult(int i, Bundle bundle) {
            MacAddress macAddress;
            if (i != 0) {
                Slog.w("CDM_AssociationRequestsProcessor", "Unknown result code:" + i);
                return;
            }
            AssociationRequest associationRequest = (AssociationRequest) bundle.getParcelable("association_request", AssociationRequest.class);
            IAssociationRequestCallback asInterface = IAssociationRequestCallback.Stub.asInterface(bundle.getBinder("application_callback"));
            ResultReceiver resultReceiver = (ResultReceiver) bundle.getParcelable("result_receiver", ResultReceiver.class);
            Objects.requireNonNull(associationRequest);
            Objects.requireNonNull(asInterface);
            Objects.requireNonNull(resultReceiver);
            if (associationRequest.isSelfManaged()) {
                macAddress = null;
            } else {
                macAddress = (MacAddress) bundle.getParcelable("mac_address", MacAddress.class);
                Objects.requireNonNull(macAddress);
            }
            AssociationRequestsProcessor.this.processAssociationRequestApproval(associationRequest, asInterface, resultReceiver, macAddress);
        }
    };
    public final PackageManagerInternal mPackageManager;
    public final CompanionDeviceManagerService mService;

    public AssociationRequestsProcessor(CompanionDeviceManagerService companionDeviceManagerService, AssociationStoreImpl associationStoreImpl) {
        this.mContext = companionDeviceManagerService.getContext();
        this.mService = companionDeviceManagerService;
        this.mPackageManager = companionDeviceManagerService.mPackageManagerInternal;
        this.mAssociationStore = associationStoreImpl;
    }

    public void processNewAssociationRequest(AssociationRequest associationRequest, String str, int i, IAssociationRequestCallback iAssociationRequestCallback) {
        Objects.requireNonNull(associationRequest, "Request MUST NOT be null");
        if (associationRequest.isSelfManaged()) {
            Objects.requireNonNull(associationRequest.getDisplayName(), "AssociationRequest.displayName MUST NOT be null.");
        }
        Objects.requireNonNull(str, "Package name MUST NOT be null");
        Objects.requireNonNull(iAssociationRequestCallback, "Callback MUST NOT be null");
        int packageUid = this.mPackageManager.getPackageUid(str, 0L, i);
        PermissionsUtils.enforcePermissionsForAssociation(this.mContext, associationRequest, packageUid);
        PackageUtils.enforceUsesCompanionDeviceFeature(this.mContext, i, str);
        if (associationRequest.isSelfManaged() && !associationRequest.isForceConfirmation() && !willAddRoleHolder(associationRequest, str, i)) {
            createAssociationAndNotifyApplication(associationRequest, str, i, null, iAssociationRequestCallback, null);
            return;
        }
        associationRequest.setPackageName(str);
        associationRequest.setUserId(i);
        associationRequest.setSkipPrompt(mayAssociateWithoutPrompt(str, i) || isPreConfirmedSharedUid(associationRequest, str, i));
        Bundle bundle = new Bundle();
        bundle.putParcelable("association_request", associationRequest);
        bundle.putBinder("application_callback", iAssociationRequestCallback.asBinder());
        bundle.putParcelable("result_receiver", Utils.prepareForIpc(this.mOnRequestConfirmationReceiver));
        Intent intent = new Intent();
        intent.setComponent(ASSOCIATION_REQUEST_APPROVAL_ACTIVITY);
        intent.putExtras(bundle);
        try {
            iAssociationRequestCallback.onAssociationPending(createPendingIntent(packageUid, intent));
        } catch (RemoteException unused) {
        }
    }

    public final boolean isPreConfirmedSharedUid(AssociationRequest associationRequest, String str, int i) {
        boolean z = false;
        try {
            int uidForSharedUser = this.mContext.getPackageManager().getUidForSharedUser("com.samsung.accessory.wmanager");
            int packageUid = this.mContext.getPackageManager().getPackageUid(str, 0);
            if (uidForSharedUser > 0 && uidForSharedUser == packageUid && associationRequest.getDeviceProfile() == null) {
                z = true;
            }
            Slog.i("CDM_AssociationRequestsProcessor", "callingUid = " + packageUid + ", userId = " + i + ", preConfirmedSharedUid = " + uidForSharedUser);
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.i("CDM_AssociationRequestsProcessor", "NameNotFoundException " + str + " or com.samsung.accessory.wmanager");
        }
        return z;
    }

    public PendingIntent buildAssociationCancellationIntent(String str, int i) {
        Objects.requireNonNull(str, "Package name MUST NOT be null");
        PackageUtils.enforceUsesCompanionDeviceFeature(this.mContext, i, str);
        int packageUid = this.mPackageManager.getPackageUid(str, 0L, i);
        Bundle bundle = new Bundle();
        bundle.putBoolean("cancel_confirmation", true);
        Intent intent = new Intent();
        intent.setComponent(ASSOCIATION_REQUEST_APPROVAL_ACTIVITY);
        intent.putExtras(bundle);
        return createPendingIntent(packageUid, intent);
    }

    public final void processAssociationRequestApproval(AssociationRequest associationRequest, IAssociationRequestCallback iAssociationRequestCallback, ResultReceiver resultReceiver, MacAddress macAddress) {
        String packageName = associationRequest.getPackageName();
        int userId = associationRequest.getUserId();
        try {
            PermissionsUtils.enforcePermissionsForAssociation(this.mContext, associationRequest, this.mPackageManager.getPackageUid(packageName, 0L, userId));
            createAssociationAndNotifyApplication(associationRequest, packageName, userId, macAddress, iAssociationRequestCallback, resultReceiver);
        } catch (SecurityException e) {
            try {
                iAssociationRequestCallback.onFailure(e.getMessage());
            } catch (RemoteException unused) {
            }
        }
    }

    public final void createAssociationAndNotifyApplication(AssociationRequest associationRequest, String str, int i, MacAddress macAddress, IAssociationRequestCallback iAssociationRequestCallback, ResultReceiver resultReceiver) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            createAssociation(i, str, macAddress, associationRequest.getDisplayName(), associationRequest.getDeviceProfile(), associationRequest.getAssociatedDevice(), associationRequest.isSelfManaged(), iAssociationRequestCallback, resultReceiver);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void createAssociation(final int i, final String str, MacAddress macAddress, CharSequence charSequence, final String str2, AssociatedDevice associatedDevice, boolean z, final IAssociationRequestCallback iAssociationRequestCallback, final ResultReceiver resultReceiver) {
        final AssociationInfo associationInfo = new AssociationInfo(this.mService.getNewAssociationIdForPackage(i, str), i, str, macAddress, charSequence, str2, associatedDevice, z, false, false, System.currentTimeMillis(), Long.MAX_VALUE, 0);
        if (str2 != null) {
            RolesUtils.addRoleHolderForAssociation(this.mService.getContext(), associationInfo, new Consumer() { // from class: com.android.server.companion.AssociationRequestsProcessor$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    AssociationRequestsProcessor.this.lambda$createAssociation$0(associationInfo, str2, iAssociationRequestCallback, resultReceiver, i, str, (Boolean) obj);
                }
            });
        } else {
            addAssociationToStore(associationInfo, null);
            sendCallbackAndFinish(associationInfo, iAssociationRequestCallback, resultReceiver);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createAssociation$0(AssociationInfo associationInfo, String str, IAssociationRequestCallback iAssociationRequestCallback, ResultReceiver resultReceiver, int i, String str2, Boolean bool) {
        if (bool.booleanValue()) {
            addAssociationToStore(associationInfo, str);
            sendCallbackAndFinish(associationInfo, iAssociationRequestCallback, resultReceiver);
            return;
        }
        Slog.e("CDM_AssociationRequestsProcessor", "Failed to add u" + i + "\\" + str2 + " to the list of " + str + " holders.");
        sendCallbackAndFinish(null, iAssociationRequestCallback, resultReceiver);
    }

    public void enableSystemDataSync(int i, int i2) {
        AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        this.mAssociationStore.updateAssociation(AssociationInfo.builder(associationById).setSystemDataSyncFlags(associationById.getSystemDataSyncFlags() | i2).build());
    }

    public void disableSystemDataSync(int i, int i2) {
        AssociationInfo associationById = this.mAssociationStore.getAssociationById(i);
        this.mAssociationStore.updateAssociation(AssociationInfo.builder(associationById).setSystemDataSyncFlags(associationById.getSystemDataSyncFlags() & (~i2)).build());
    }

    public final void addAssociationToStore(AssociationInfo associationInfo, String str) {
        Slog.i("CDM_AssociationRequestsProcessor", "New CDM association created=" + associationInfo);
        this.mAssociationStore.addAssociation(associationInfo);
        this.mService.updateSpecialAccessPermissionForAssociatedPackage(associationInfo);
        MetricUtils.logCreateAssociation(str);
    }

    public final void sendCallbackAndFinish(AssociationInfo associationInfo, IAssociationRequestCallback iAssociationRequestCallback, ResultReceiver resultReceiver) {
        if (associationInfo == null) {
            if (iAssociationRequestCallback != null) {
                try {
                    iAssociationRequestCallback.onFailure("internal_error");
                } catch (RemoteException unused) {
                }
            }
            if (resultReceiver != null) {
                resultReceiver.send(3, new Bundle());
                return;
            }
            return;
        }
        if (iAssociationRequestCallback != null) {
            try {
                iAssociationRequestCallback.onAssociationCreated(associationInfo);
            } catch (RemoteException unused2) {
            }
        }
        if (resultReceiver != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("association", associationInfo);
            resultReceiver.send(0, bundle);
        }
    }

    public final boolean willAddRoleHolder(AssociationRequest associationRequest, final String str, final int i) {
        if (associationRequest.getDeviceProfile() == null) {
            return false;
        }
        return !((Boolean) Binder.withCleanCallingIdentity(new FunctionalUtils.ThrowingSupplier
        /*  JADX ERROR: Method code generation error
            jadx.core.utils.exceptions.CodegenException: Error generate insn: 0x0019: RETURN 
              (wrap:boolean:NOT 
              (wrap:boolean:0x0013: INVOKE 
              (wrap:java.lang.Boolean:0x0011: CHECK_CAST (java.lang.Boolean) (wrap:java.lang.Object:0x000d: INVOKE 
              (wrap:com.android.internal.util.FunctionalUtils$ThrowingSupplier:0x000a: CONSTRUCTOR 
              (r1v0 'this' com.android.server.companion.AssociationRequestsProcessor A[DONT_INLINE, IMMUTABLE_TYPE, THIS])
              (r4v0 'i' int A[DONT_INLINE])
              (r3v0 'str' java.lang.String A[DONT_INLINE])
              (r2 I:java.lang.String A[DONT_INLINE])
             A[DONT_GENERATE, MD:(com.android.server.companion.AssociationRequestsProcessor, int, java.lang.String, java.lang.String):void (m), REMOVE, WRAPPED] (LINE:415) call: com.android.server.companion.AssociationRequestsProcessor$$ExternalSyntheticLambda1.<init>(com.android.server.companion.AssociationRequestsProcessor, int, java.lang.String, java.lang.String):void type: CONSTRUCTOR)
             STATIC call: android.os.Binder.withCleanCallingIdentity(com.android.internal.util.FunctionalUtils$ThrowingSupplier):java.lang.Object A[DONT_GENERATE, MD:(com.android.internal.util.FunctionalUtils$ThrowingSupplier):java.lang.Object (s), REMOVE, WRAPPED] (LINE:415)))
             VIRTUAL call: java.lang.Boolean.booleanValue():boolean A[DONT_GENERATE, MD:():boolean (c), REMOVE, WRAPPED] (LINE:415))
             A[WRAPPED])
             (LINE:415) in method: com.android.server.companion.AssociationRequestsProcessor.willAddRoleHolder(android.companion.AssociationRequest, java.lang.String, int):boolean, file: classes.dex
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:310)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:273)
            	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:94)
            	at jadx.core.dex.nodes.IBlock.generate(IBlock.java:15)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.dex.regions.Region.generate(Region.java:35)
            	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
            	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:297)
            	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:276)
            	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:406)
            	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:335)
            	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$3(ClassGen.java:301)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:184)
            	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
            	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
            	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:261)
            	at java.base/java.util.stream.ReferencePipeline$7$1FlatMap.end(ReferencePipeline.java:285)
            	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:571)
            	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:560)
            	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:151)
            	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:174)
            	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:265)
            	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:636)
            	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:297)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:286)
            	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:270)
            	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:161)
            	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:103)
            	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:45)
            	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:34)
            	at jadx.core.codegen.CodeGen.generate(CodeGen.java:22)
            	at jadx.core.ProcessClass.process(ProcessClass.java:79)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:402)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:390)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:340)
            Caused by: java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.getCodeVar()" because the return value of "jadx.core.dex.instructions.args.RegisterArg.getSVar()" is null
            	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:836)
            	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:730)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:418)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.generateMethodArguments(InsnGen.java:1143)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:910)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:345)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.addArgDot(InsnGen.java:97)
            	at jadx.core.codegen.InsnGen.makeInvoke(InsnGen.java:878)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:422)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:104)
            	at jadx.core.codegen.InsnGen.oneArgInsn(InsnGen.java:689)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:362)
            	at jadx.core.codegen.InsnGen.addWrappedArg(InsnGen.java:145)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:121)
            	at jadx.core.codegen.InsnGen.addArg(InsnGen.java:108)
            	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:368)
            	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:303)
            	... 39 more
            */
        /*
            this = this;
            java.lang.String r2 = r2.getDeviceProfile()
            if (r2 != 0) goto L8
            r1 = 0
            return r1
        L8:
            com.android.server.companion.AssociationRequestsProcessor$$ExternalSyntheticLambda1 r0 = new com.android.server.companion.AssociationRequestsProcessor$$ExternalSyntheticLambda1
            r0.<init>()
            java.lang.Object r1 = android.os.Binder.withCleanCallingIdentity(r0)
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            r1 = r1 ^ 1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.companion.AssociationRequestsProcessor.willAddRoleHolder(android.companion.AssociationRequest, java.lang.String, int):boolean");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Boolean lambda$willAddRoleHolder$1(int i, String str, String str2) {
        return Boolean.valueOf(RolesUtils.isRoleHolder(this.mContext, i, str, str2));
    }

    public final PendingIntent createPendingIntent(int i, Intent intent) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return PendingIntent.getActivityAsUser(this.mContext, i, intent, 1409286144, null, UserHandle.CURRENT);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean mayAssociateWithoutPrompt(String str, int i) {
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.config_screenBrighteningThresholds);
        boolean z = false;
        if (!ArrayUtils.contains(stringArray, str)) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        Iterator it = this.mAssociationStore.getAssociationsForPackage(i, str).iterator();
        int i2 = 0;
        while (true) {
            if (it.hasNext()) {
                if ((currentTimeMillis - ((AssociationInfo) it.next()).getTimeApprovedMs() < ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) && (i2 = i2 + 1) >= 5) {
                    Slog.w("CDM_AssociationRequestsProcessor", "Too many associations: " + str + " already associated " + i2 + " devices within the last " + ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS + "ms");
                    return false;
                }
            } else {
                String[] stringArray2 = this.mContext.getResources().getStringArray(R.array.config_satellite_providers);
                HashSet hashSet = new HashSet();
                for (int i3 = 0; i3 < stringArray.length; i3++) {
                    if (stringArray[i3].equals(str)) {
                        hashSet.add(stringArray2[i3].replaceAll(XmlUtils.STRING_ARRAY_SEPARATOR, ""));
                    }
                }
                String[] computeSignaturesSha256Digests = android.util.PackageUtils.computeSignaturesSha256Digests(this.mPackageManager.getPackage(str).getSigningDetails().getSignatures());
                int length = computeSignaturesSha256Digests.length;
                int i4 = 0;
                while (true) {
                    if (i4 >= length) {
                        break;
                    }
                    if (hashSet.contains(computeSignaturesSha256Digests[i4])) {
                        z = true;
                        break;
                    }
                    i4++;
                }
                if (!z) {
                    Slog.w("CDM_AssociationRequestsProcessor", "Certificate mismatch for allowlisted package " + str);
                }
                return z;
            }
        }
    }
}
