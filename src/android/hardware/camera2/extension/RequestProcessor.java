package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.extension.IRequestCallback;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes2.dex */
public final class RequestProcessor {
    private static final String TAG = "RequestProcessor";
    private final IRequestProcessorImpl mRequestProcessor;
    private final long mVendorId;

    public interface RequestCallback {
        void onCaptureBufferLost(Request request, long j, int i);

        void onCaptureCompleted(Request request, TotalCaptureResult totalCaptureResult);

        void onCaptureFailed(Request request, android.hardware.camera2.CaptureFailure captureFailure);

        void onCaptureProgressed(Request request, CaptureResult captureResult);

        void onCaptureSequenceAborted(int i);

        void onCaptureSequenceCompleted(int i, long j);

        void onCaptureStarted(Request request, long j, long j2);
    }

    RequestProcessor(IRequestProcessorImpl iRequestProcessorImpl, long j) {
        this.mRequestProcessor = iRequestProcessorImpl;
        this.mVendorId = j;
    }

    public static final class Request {
        private final List<Integer> mOutputIds;
        private final List<Pair<CaptureRequest.Key, Object>> mParameters;
        private final int mTemplateId;

        public Request(List<Integer> list, List<Pair<CaptureRequest.Key, Object>> list2, int i) {
            this.mOutputIds = list;
            this.mParameters = list2;
            this.mTemplateId = i;
        }

        List<Integer> getOutputConfigIds() {
            return this.mOutputIds;
        }

        public List<Pair<CaptureRequest.Key, Object>> getParameters() {
            return this.mParameters;
        }

        Integer getTemplateId() {
            return Integer.valueOf(this.mTemplateId);
        }

        List<OutputConfigId> getTargetIds() {
            ArrayList arrayList = new ArrayList(this.mOutputIds.size());
            int i = 0;
            for (Integer num : this.mOutputIds) {
                OutputConfigId outputConfigId = new OutputConfigId();
                outputConfigId.id = num.intValue();
                arrayList.add(i, outputConfigId);
                i++;
            }
            return arrayList;
        }

        static CameraMetadataNative getParametersMetadata(long j, List<Pair<CaptureRequest.Key, Object>> list) {
            CameraMetadataNative cameraMetadataNative = new CameraMetadataNative();
            cameraMetadataNative.setVendorId(j);
            for (Pair<CaptureRequest.Key, Object> pair : list) {
                cameraMetadataNative.set((CaptureRequest.Key<CaptureRequest.Key>) pair.first, (CaptureRequest.Key) pair.second);
            }
            return cameraMetadataNative;
        }

        static List<android.hardware.camera2.extension.Request> initializeParcelable(long j, List<Request> list) {
            ArrayList arrayList = new ArrayList(list.size());
            int i = 0;
            for (Request request : list) {
                android.hardware.camera2.extension.Request request2 = new android.hardware.camera2.extension.Request();
                request2.requestId = i;
                request2.templateId = request.getTemplateId().intValue();
                request2.targetOutputConfigIds = request.getTargetIds();
                request2.parameters = getParametersMetadata(j, request.getParameters());
                arrayList.add(request2.requestId, request2);
                i++;
            }
            return arrayList;
        }
    }

    public int submit(Request request, Executor executor, RequestCallback requestCallback) throws CameraAccessException {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(0, request);
        try {
            int iSubmit = this.mRequestProcessor.submit(Request.initializeParcelable(this.mVendorId, arrayList).get(0), new RequestCallbackImpl(arrayList, requestCallback, executor));
            if (iSubmit != -1) {
                return iSubmit;
            }
            throw new CameraAccessException(3, "Failed to submit capture request");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int submitBurst(List<Request> list, Executor executor, RequestCallback requestCallback) throws CameraAccessException {
        try {
            int iSubmitBurst = this.mRequestProcessor.submitBurst(Request.initializeParcelable(this.mVendorId, list), new RequestCallbackImpl(list, requestCallback, executor));
            if (iSubmitBurst != -1) {
                return iSubmitBurst;
            }
            throw new CameraAccessException(3, "Failed to submit burst request");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int setRepeating(Request request, Executor executor, RequestCallback requestCallback) throws CameraAccessException {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(0, request);
        try {
            int repeating = this.mRequestProcessor.setRepeating(Request.initializeParcelable(this.mVendorId, arrayList).get(0), new RequestCallbackImpl(arrayList, requestCallback, executor));
            if (repeating != -1) {
                return repeating;
            }
            throw new CameraAccessException(3, "Failed to set the repeating request");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void abortCaptures() {
        try {
            this.mRequestProcessor.abortCaptures();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopRepeating() {
        try {
            this.mRequestProcessor.stopRepeating();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class RequestCallbackImpl extends IRequestCallback.Stub {
        private final RequestCallback mCallback;
        private final Executor mExecutor;
        private final List<Request> mRequests;

        public RequestCallbackImpl(List<Request> list, RequestCallback requestCallback, Executor executor) {
            this.mCallback = requestCallback;
            this.mRequests = list;
            this.mExecutor = executor;
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureStarted(final int i, final long j, final long j2) {
            if (this.mRequests.get(i) != null) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onCaptureStarted$0(i, j, j2);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(int i, long j, long j2) {
            this.mCallback.onCaptureStarted(this.mRequests.get(i), j, j2);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureProgressed(final int i, ParcelCaptureResult parcelCaptureResult) {
            if (this.mRequests.get(i) != null) {
                final CaptureResult captureResult = new CaptureResult(parcelCaptureResult.cameraId, parcelCaptureResult.results, parcelCaptureResult.parent, parcelCaptureResult.sequenceId, parcelCaptureResult.frameNumber);
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onCaptureProgressed$1(i, captureResult);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProgressed$1(int i, CaptureResult captureResult) {
            this.mCallback.onCaptureProgressed(this.mRequests.get(i), captureResult);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureCompleted(final int i, ParcelTotalCaptureResult parcelTotalCaptureResult) {
            if (this.mRequests.get(i) != null) {
                PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr = new PhysicalCaptureResultInfo[0];
                if (parcelTotalCaptureResult.physicalResult != null && !parcelTotalCaptureResult.physicalResult.isEmpty()) {
                    physicalCaptureResultInfoArr = (PhysicalCaptureResultInfo[]) parcelTotalCaptureResult.physicalResult.toArray(new PhysicalCaptureResultInfo[parcelTotalCaptureResult.physicalResult.size()]);
                }
                PhysicalCaptureResultInfo[] physicalCaptureResultInfoArr2 = physicalCaptureResultInfoArr;
                ArrayList arrayList = new ArrayList(parcelTotalCaptureResult.partials.size());
                for (ParcelCaptureResult parcelCaptureResult : parcelTotalCaptureResult.partials) {
                    arrayList.add(new CaptureResult(parcelCaptureResult.cameraId, parcelCaptureResult.results, parcelCaptureResult.parent, parcelCaptureResult.sequenceId, parcelCaptureResult.frameNumber));
                }
                final TotalCaptureResult totalCaptureResult = new TotalCaptureResult(parcelTotalCaptureResult.logicalCameraId, parcelTotalCaptureResult.results, parcelTotalCaptureResult.parent, parcelTotalCaptureResult.sequenceId, parcelTotalCaptureResult.frameNumber, arrayList, parcelTotalCaptureResult.sessionId, physicalCaptureResultInfoArr2);
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onCaptureCompleted$2(i, totalCaptureResult);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$2(int i, TotalCaptureResult totalCaptureResult) {
            this.mCallback.onCaptureCompleted(this.mRequests.get(i), totalCaptureResult);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureFailed(final int i, CaptureFailure captureFailure) {
            if (this.mRequests.get(i) != null) {
                final android.hardware.camera2.CaptureFailure captureFailure2 = new android.hardware.camera2.CaptureFailure(captureFailure.request, captureFailure.reason, captureFailure.dropped, captureFailure.sequenceId, captureFailure.frameNumber, captureFailure.errorPhysicalCameraId);
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onCaptureFailed$3(i, captureFailure2);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$3(int i, android.hardware.camera2.CaptureFailure captureFailure) {
            this.mCallback.onCaptureFailed(this.mRequests.get(i), captureFailure);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureBufferLost(final int i, final long j, final int i2) {
            if (this.mRequests.get(i) != null) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.lambda$onCaptureBufferLost$4(i, j, i2);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + i + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureBufferLost$4(int i, long j, int i2) {
            this.mCallback.onCaptureBufferLost(this.mRequests.get(i), j, i2);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceCompleted(final int i, final long j) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureSequenceCompleted$5(i, j);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$5(int i, long j) {
            this.mCallback.onCaptureSequenceCompleted(i, j);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceAborted(final int i) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.lambda$onCaptureSequenceAborted$6(i);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$6(int i) {
            this.mCallback.onCaptureSequenceAborted(i);
        }
    }
}
