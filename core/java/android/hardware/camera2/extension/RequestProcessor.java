package android.hardware.camera2.extension;

import android.annotation.SystemApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.extension.IRequestCallback;
import android.hardware.camera2.extension.RequestProcessor;
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

    RequestProcessor(IRequestProcessorImpl requestProcessor, long vendorId) {
        this.mRequestProcessor = requestProcessor;
        this.mVendorId = vendorId;
    }

    public static final class Request {
        private final List<Integer> mOutputIds;
        private final List<Pair<CaptureRequest.Key, Object>> mParameters;
        private final int mTemplateId;

        public Request(List<Integer> outputConfigIds, List<Pair<CaptureRequest.Key, Object>> parameters, int templateId) {
            this.mOutputIds = outputConfigIds;
            this.mParameters = parameters;
            this.mTemplateId = templateId;
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
            ArrayList<OutputConfigId> ret = new ArrayList<>(this.mOutputIds.size());
            int idx = 0;
            for (Integer outputId : this.mOutputIds) {
                OutputConfigId configId = new OutputConfigId();
                configId.id = outputId.intValue();
                ret.add(idx, configId);
                idx++;
            }
            return ret;
        }

        static CameraMetadataNative getParametersMetadata(long vendorId, List<Pair<CaptureRequest.Key, Object>> parameters) {
            CameraMetadataNative ret = new CameraMetadataNative();
            ret.setVendorId(vendorId);
            for (Pair<CaptureRequest.Key, Object> pair : parameters) {
                ret.set((CaptureRequest.Key<CaptureRequest.Key>) pair.first, (CaptureRequest.Key) pair.second);
            }
            return ret;
        }

        static List<android.hardware.camera2.extension.Request> initializeParcelable(long vendorId, List<Request> requests) {
            ArrayList<android.hardware.camera2.extension.Request> ret = new ArrayList<>(requests.size());
            int requestId = 0;
            for (Request req : requests) {
                android.hardware.camera2.extension.Request request = new android.hardware.camera2.extension.Request();
                request.requestId = requestId;
                request.templateId = req.getTemplateId().intValue();
                request.targetOutputConfigIds = req.getTargetIds();
                request.parameters = getParametersMetadata(vendorId, req.getParameters());
                ret.add(request.requestId, request);
                requestId++;
            }
            return ret;
        }
    }

    public int submit(Request request, Executor executor, RequestCallback callback) throws CameraAccessException {
        ArrayList<Request> requests = new ArrayList<>(1);
        requests.add(0, request);
        List<android.hardware.camera2.extension.Request> parcelableRequests = Request.initializeParcelable(this.mVendorId, requests);
        try {
            int ret = this.mRequestProcessor.submit(parcelableRequests.get(0), new RequestCallbackImpl(requests, callback, executor));
            if (ret == -1) {
                throw new CameraAccessException(3, "Failed to submit capture request");
            }
            return ret;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int submitBurst(List<Request> requests, Executor executor, RequestCallback callback) throws CameraAccessException {
        List<android.hardware.camera2.extension.Request> parcelableRequests = Request.initializeParcelable(this.mVendorId, requests);
        try {
            int ret = this.mRequestProcessor.submitBurst(parcelableRequests, new RequestCallbackImpl(requests, callback, executor));
            if (ret != -1) {
                return ret;
            }
            throw new CameraAccessException(3, "Failed to submit burst request");
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public int setRepeating(Request request, Executor executor, RequestCallback callback) throws CameraAccessException {
        ArrayList<Request> requests = new ArrayList<>(1);
        requests.add(0, request);
        List<android.hardware.camera2.extension.Request> parcelableRequests = Request.initializeParcelable(this.mVendorId, requests);
        try {
            int ret = this.mRequestProcessor.setRepeating(parcelableRequests.get(0), new RequestCallbackImpl(requests, callback, executor));
            if (ret == -1) {
                throw new CameraAccessException(3, "Failed to set the repeating request");
            }
            return ret;
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

        public RequestCallbackImpl(List<Request> requests, RequestCallback callback, Executor executor) {
            this.mCallback = callback;
            this.mRequests = requests;
            this.mExecutor = executor;
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureStarted(final int requestId, final long frameNumber, final long timestamp) {
            if (this.mRequests.get(requestId) != null) {
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureStarted$0(requestId, frameNumber, timestamp);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + requestId + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureStarted$0(int requestId, long frameNumber, long timestamp) {
            this.mCallback.onCaptureStarted(this.mRequests.get(requestId), frameNumber, timestamp);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureProgressed(final int requestId, ParcelCaptureResult partialResult) {
            if (this.mRequests.get(requestId) != null) {
                final CaptureResult result = new CaptureResult(partialResult.cameraId, partialResult.results, partialResult.parent, partialResult.sequenceId, partialResult.frameNumber);
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda3
                        @Override // java.lang.Runnable
                        public final void run() {
                            RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureProgressed$1(requestId, result);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + requestId + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureProgressed$1(int requestId, CaptureResult result) {
            this.mCallback.onCaptureProgressed(this.mRequests.get(requestId), result);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureCompleted(final int requestId, ParcelTotalCaptureResult totalCaptureResult) {
            if (this.mRequests.get(requestId) != null) {
                PhysicalCaptureResultInfo[] physicalResults = new PhysicalCaptureResultInfo[0];
                if (totalCaptureResult.physicalResult != null && !totalCaptureResult.physicalResult.isEmpty()) {
                    int count = totalCaptureResult.physicalResult.size();
                    physicalResults = (PhysicalCaptureResultInfo[]) totalCaptureResult.physicalResult.toArray(new PhysicalCaptureResultInfo[count]);
                }
                ArrayList<CaptureResult> partials = new ArrayList<>(totalCaptureResult.partials.size());
                for (ParcelCaptureResult parcelResult : totalCaptureResult.partials) {
                    partials.add(new CaptureResult(parcelResult.cameraId, parcelResult.results, parcelResult.parent, parcelResult.sequenceId, parcelResult.frameNumber));
                }
                final TotalCaptureResult result = new TotalCaptureResult(totalCaptureResult.logicalCameraId, totalCaptureResult.results, totalCaptureResult.parent, totalCaptureResult.sequenceId, totalCaptureResult.frameNumber, partials, totalCaptureResult.sessionId, physicalResults);
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureCompleted$2(requestId, result);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + requestId + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureCompleted$2(int requestId, TotalCaptureResult result) {
            this.mCallback.onCaptureCompleted(this.mRequests.get(requestId), result);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureFailed(final int requestId, CaptureFailure captureFailure) {
            if (this.mRequests.get(requestId) != null) {
                final android.hardware.camera2.CaptureFailure failure = new android.hardware.camera2.CaptureFailure(captureFailure.request, captureFailure.reason, captureFailure.dropped, captureFailure.sequenceId, captureFailure.frameNumber, captureFailure.errorPhysicalCameraId);
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureFailed$3(requestId, failure);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + requestId + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureFailed$3(int requestId, android.hardware.camera2.CaptureFailure failure) {
            this.mCallback.onCaptureFailed(this.mRequests.get(requestId), failure);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureBufferLost(final int requestId, final long frameNumber, final int outputStreamId) {
            if (this.mRequests.get(requestId) != null) {
                long ident = Binder.clearCallingIdentity();
                try {
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureBufferLost$4(requestId, frameNumber, outputStreamId);
                        }
                    });
                    return;
                } finally {
                    Binder.restoreCallingIdentity(ident);
                }
            }
            Log.e(RequestProcessor.TAG, "Request id: " + requestId + " not found!");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureBufferLost$4(int requestId, long frameNumber, int outputStreamId) {
            this.mCallback.onCaptureBufferLost(this.mRequests.get(requestId), frameNumber, outputStreamId);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceCompleted(final int sequenceId, final long frameNumber) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureSequenceCompleted$5(sequenceId, frameNumber);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceCompleted$5(int sequenceId, long frameNumber) {
            this.mCallback.onCaptureSequenceCompleted(sequenceId, frameNumber);
        }

        @Override // android.hardware.camera2.extension.IRequestCallback
        public void onCaptureSequenceAborted(final int sequenceId) {
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.extension.RequestProcessor$RequestCallbackImpl$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        RequestProcessor.RequestCallbackImpl.this.lambda$onCaptureSequenceAborted$6(sequenceId);
                    }
                });
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCaptureSequenceAborted$6(int sequenceId) {
            this.mCallback.onCaptureSequenceAborted(sequenceId);
        }
    }
}
