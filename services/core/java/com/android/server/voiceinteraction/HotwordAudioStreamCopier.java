package com.android.server.voiceinteraction;

import android.app.AppOpsManager;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.service.voice.HotwordAudioStream;
import android.service.voice.HotwordDetectedResult;
import android.util.Slog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class HotwordAudioStreamCopier {
    static final int DEFAULT_COPY_BUFFER_LENGTH_BYTES = 32768;
    static final int MAX_COPY_BUFFER_LENGTH_BYTES = 65536;
    public final AppOpsManager mAppOpsManager;
    public final int mDetectorType;
    public final ExecutorService mExecutorService = Executors.newCachedThreadPool();
    public final String mVoiceInteractorAttributionTag;
    public final String mVoiceInteractorPackageName;
    public final int mVoiceInteractorUid;

    public final class CopyTaskInfo {
        public final int mCopyBufferLength;
        public final ParcelFileDescriptor mSink;
        public final ParcelFileDescriptor mSource;

        public CopyTaskInfo(
                ParcelFileDescriptor parcelFileDescriptor,
                ParcelFileDescriptor parcelFileDescriptor2,
                int i) {
            this.mSource = parcelFileDescriptor;
            this.mSink = parcelFileDescriptor2;
            this.mCopyBufferLength = i;
        }
    }

    public final class HotwordDetectedResultCopyTask implements Runnable {
        public final List mCopyTaskInfos;
        public final ExecutorService mExecutorService = Executors.newCachedThreadPool();
        public final String mResultTaskId;
        public final boolean mShouldNotifyAppOpsManager;
        public final int mTotalInitialAudioSizeBytes;
        public final int mTotalMetadataSizeBytes;

        public HotwordDetectedResultCopyTask(String str, List list, int i, int i2, boolean z) {
            this.mResultTaskId = str;
            this.mCopyTaskInfos = list;
            this.mTotalMetadataSizeBytes = i;
            this.mTotalInitialAudioSizeBytes = i2;
            this.mShouldNotifyAppOpsManager = z;
        }

        public final void bestEffortPropagateError(String str) {
            try {
                for (CopyTaskInfo copyTaskInfo : this.mCopyTaskInfos) {
                    copyTaskInfo.mSource.closeWithError(str);
                    copyTaskInfo.mSink.closeWithError(str);
                }
                HotwordAudioStreamCopier hotwordAudioStreamCopier = HotwordAudioStreamCopier.this;
                HotwordMetricsLogger.writeAudioEgressEvent(
                        hotwordAudioStreamCopier.mDetectorType,
                        10,
                        hotwordAudioStreamCopier.mVoiceInteractorUid,
                        0,
                        0,
                        this.mCopyTaskInfos.size());
            } catch (IOException e) {
                Slog.e(
                        "HotwordAudioStreamCopier",
                        this.mResultTaskId + ": Failed to propagate error",
                        e);
            }
        }

        @Override // java.lang.Runnable
        public final void run() {
            HotwordAudioStreamCopier hotwordAudioStreamCopier;
            AppOpsManager appOpsManager;
            int i;
            String str;
            Thread.currentThread().setName("Copy-" + this.mResultTaskId);
            int size = this.mCopyTaskInfos.size();
            ArrayList arrayList = new ArrayList(size);
            for (int i2 = 0; i2 < size; i2++) {
                CopyTaskInfo copyTaskInfo = (CopyTaskInfo) this.mCopyTaskInfos.get(i2);
                String str2 = this.mResultTaskId + "@" + i2;
                ParcelFileDescriptor parcelFileDescriptor = copyTaskInfo.mSource;
                ParcelFileDescriptor parcelFileDescriptor2 = copyTaskInfo.mSink;
                int i3 = copyTaskInfo.mCopyBufferLength;
                HotwordAudioStreamCopier hotwordAudioStreamCopier2 = HotwordAudioStreamCopier.this;
                arrayList.add(
                        new SingleAudioStreamCopyTask(
                                str2,
                                parcelFileDescriptor,
                                parcelFileDescriptor2,
                                i3,
                                hotwordAudioStreamCopier2.mDetectorType,
                                hotwordAudioStreamCopier2.mVoiceInteractorUid));
            }
            if (this.mShouldNotifyAppOpsManager) {
                HotwordAudioStreamCopier hotwordAudioStreamCopier3 = HotwordAudioStreamCopier.this;
                if (hotwordAudioStreamCopier3.mAppOpsManager.startOpNoThrow(
                                "android:record_audio_hotword",
                                hotwordAudioStreamCopier3.mVoiceInteractorUid,
                                hotwordAudioStreamCopier3.mVoiceInteractorPackageName,
                                hotwordAudioStreamCopier3.mVoiceInteractorAttributionTag,
                                "Streaming hotword audio to VoiceInteractionService")
                        != 0) {
                    HotwordAudioStreamCopier hotwordAudioStreamCopier4 =
                            HotwordAudioStreamCopier.this;
                    HotwordMetricsLogger.writeAudioEgressEvent(
                            hotwordAudioStreamCopier4.mDetectorType,
                            4,
                            hotwordAudioStreamCopier4.mVoiceInteractorUid,
                            0,
                            0,
                            size);
                    bestEffortPropagateError(
                            "Failed to obtain RECORD_AUDIO_HOTWORD permission for voice interactor"
                                + " with uid="
                                    + HotwordAudioStreamCopier.this.mVoiceInteractorUid
                                    + " packageName="
                                    + HotwordAudioStreamCopier.this.mVoiceInteractorPackageName
                                    + " attributionTag="
                                    + HotwordAudioStreamCopier.this.mVoiceInteractorAttributionTag);
                    return;
                }
            }
            try {
                try {
                    HotwordAudioStreamCopier hotwordAudioStreamCopier5 =
                            HotwordAudioStreamCopier.this;
                    HotwordMetricsLogger.writeAudioEgressEvent(
                            hotwordAudioStreamCopier5.mDetectorType,
                            1,
                            hotwordAudioStreamCopier5.mVoiceInteractorUid,
                            this.mTotalInitialAudioSizeBytes,
                            this.mTotalMetadataSizeBytes,
                            size);
                    this.mExecutorService.invokeAll(arrayList);
                    int i4 = this.mTotalInitialAudioSizeBytes;
                    Iterator it = arrayList.iterator();
                    int i5 = i4;
                    while (it.hasNext()) {
                        i5 += ((SingleAudioStreamCopyTask) it.next()).mTotalCopiedBytes;
                    }
                    Slog.i(
                            "HotwordAudioStreamCopier",
                            this.mResultTaskId
                                    + ": Task was completed. Total bytes egressed: "
                                    + i5
                                    + " (including "
                                    + this.mTotalInitialAudioSizeBytes
                                    + " bytes NOT streamed), total metadata bundle size bytes: "
                                    + this.mTotalMetadataSizeBytes);
                    HotwordAudioStreamCopier hotwordAudioStreamCopier6 =
                            HotwordAudioStreamCopier.this;
                    HotwordMetricsLogger.writeAudioEgressEvent(
                            hotwordAudioStreamCopier6.mDetectorType,
                            2,
                            hotwordAudioStreamCopier6.mVoiceInteractorUid,
                            i5,
                            this.mTotalMetadataSizeBytes,
                            size);
                } catch (InterruptedException e) {
                    int i6 = this.mTotalInitialAudioSizeBytes;
                    Iterator it2 = arrayList.iterator();
                    int i7 = i6;
                    while (it2.hasNext()) {
                        i7 += ((SingleAudioStreamCopyTask) it2.next()).mTotalCopiedBytes;
                    }
                    HotwordAudioStreamCopier hotwordAudioStreamCopier7 =
                            HotwordAudioStreamCopier.this;
                    HotwordMetricsLogger.writeAudioEgressEvent(
                            hotwordAudioStreamCopier7.mDetectorType,
                            3,
                            hotwordAudioStreamCopier7.mVoiceInteractorUid,
                            i7,
                            this.mTotalMetadataSizeBytes,
                            size);
                    Slog.i(
                            "HotwordAudioStreamCopier",
                            this.mResultTaskId
                                    + ": Task was interrupted. Total bytes egressed: "
                                    + i7
                                    + " (including "
                                    + this.mTotalInitialAudioSizeBytes
                                    + " bytes NOT streamed), total metadata bundle size bytes: "
                                    + this.mTotalMetadataSizeBytes);
                    bestEffortPropagateError(e.getMessage());
                    if (!this.mShouldNotifyAppOpsManager) {
                        return;
                    }
                    hotwordAudioStreamCopier = HotwordAudioStreamCopier.this;
                    appOpsManager = hotwordAudioStreamCopier.mAppOpsManager;
                    i = hotwordAudioStreamCopier.mVoiceInteractorUid;
                    str = hotwordAudioStreamCopier.mVoiceInteractorPackageName;
                }
                if (this.mShouldNotifyAppOpsManager) {
                    hotwordAudioStreamCopier = HotwordAudioStreamCopier.this;
                    appOpsManager = hotwordAudioStreamCopier.mAppOpsManager;
                    i = hotwordAudioStreamCopier.mVoiceInteractorUid;
                    str = hotwordAudioStreamCopier.mVoiceInteractorPackageName;
                    appOpsManager.finishOp(
                            "android:record_audio_hotword",
                            i,
                            str,
                            hotwordAudioStreamCopier.mVoiceInteractorAttributionTag);
                }
            } catch (Throwable th) {
                if (this.mShouldNotifyAppOpsManager) {
                    HotwordAudioStreamCopier hotwordAudioStreamCopier8 =
                            HotwordAudioStreamCopier.this;
                    hotwordAudioStreamCopier8.mAppOpsManager.finishOp(
                            "android:record_audio_hotword",
                            hotwordAudioStreamCopier8.mVoiceInteractorUid,
                            hotwordAudioStreamCopier8.mVoiceInteractorPackageName,
                            hotwordAudioStreamCopier8.mVoiceInteractorAttributionTag);
                }
                throw th;
            }
        }
    }

    public final class SingleAudioStreamCopyTask implements Callable {
        public final ParcelFileDescriptor mAudioSink;
        public final ParcelFileDescriptor mAudioSource;
        public final int mCopyBufferLength;
        public final int mDetectorType;
        public final String mStreamTaskId;
        public volatile int mTotalCopiedBytes = 0;
        public final int mUid;

        public SingleAudioStreamCopyTask(
                String str,
                ParcelFileDescriptor parcelFileDescriptor,
                ParcelFileDescriptor parcelFileDescriptor2,
                int i,
                int i2,
                int i3) {
            this.mStreamTaskId = str;
            this.mAudioSource = parcelFileDescriptor;
            this.mAudioSink = parcelFileDescriptor2;
            this.mCopyBufferLength = i;
            this.mDetectorType = i2;
            this.mUid = i3;
        }

        /* JADX WARN: Code restructure failed: missing block: B:33:0x00c3, code lost:

           if (r3 != null) goto L17;
        */
        @Override // java.util.concurrent.Callable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object call() {
            /*
                r12 = this;
                java.lang.String r0 = "HotwordAudioStreamCopier"
                java.lang.Thread r1 = java.lang.Thread.currentThread()
                java.lang.StringBuilder r2 = new java.lang.StringBuilder
                java.lang.String r3 = "Copy-"
                r2.<init>(r3)
                java.lang.String r3 = r12.mStreamTaskId
                r2.append(r3)
                java.lang.String r2 = r2.toString()
                r1.setName(r2)
                r1 = 0
                android.os.ParcelFileDescriptor$AutoCloseInputStream r2 = new android.os.ParcelFileDescriptor$AutoCloseInputStream     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L87
                android.os.ParcelFileDescriptor r3 = r12.mAudioSource     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L87
                r2.<init>(r3)     // Catch: java.lang.Throwable -> L84 java.io.IOException -> L87
                android.os.ParcelFileDescriptor$AutoCloseOutputStream r3 = new android.os.ParcelFileDescriptor$AutoCloseOutputStream     // Catch: java.lang.Throwable -> L7e java.io.IOException -> L81
                android.os.ParcelFileDescriptor r4 = r12.mAudioSink     // Catch: java.lang.Throwable -> L7e java.io.IOException -> L81
                r3.<init>(r4)     // Catch: java.lang.Throwable -> L7e java.io.IOException -> L81
                int r4 = r12.mCopyBufferLength     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                byte[] r4 = new byte[r4]     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            L2c:
                boolean r5 = java.lang.Thread.interrupted()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                if (r5 == 0) goto L4f
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                r4.<init>()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                java.lang.String r5 = r12.mStreamTaskId     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                r4.append(r5)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                java.lang.String r5 = ": SingleAudioStreamCopyTask task was interrupted"
                r4.append(r5)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                android.util.Slog.e(r0, r4)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                goto L6b
            L49:
                r12 = move-exception
            L4a:
                r1 = r2
                goto Lc7
            L4d:
                r4 = move-exception
                goto L8a
            L4f:
                int r5 = r2.read(r4)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                if (r5 >= 0) goto L72
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                r4.<init>()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                java.lang.String r5 = r12.mStreamTaskId     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                r4.append(r5)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                java.lang.String r5 = ": Reached end of audio stream"
                r4.append(r5)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                java.lang.String r4 = r4.toString()     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                android.util.Slog.i(r0, r4)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
            L6b:
                r2.close()
            L6e:
                r3.close()
                goto Lc6
            L72:
                if (r5 <= 0) goto L2c
                r6 = 0
                r3.write(r4, r6, r5)     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                int r6 = r12.mTotalCopiedBytes     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                int r6 = r6 + r5
                r12.mTotalCopiedBytes = r6     // Catch: java.lang.Throwable -> L49 java.io.IOException -> L4d
                goto L2c
            L7e:
                r12 = move-exception
                r3 = r1
                goto L4a
            L81:
                r4 = move-exception
                r3 = r1
                goto L8a
            L84:
                r12 = move-exception
                r3 = r1
                goto Lc7
            L87:
                r4 = move-exception
                r2 = r1
                r3 = r2
            L8a:
                android.os.ParcelFileDescriptor r5 = r12.mAudioSource     // Catch: java.lang.Throwable -> L49
                java.lang.String r6 = r4.getMessage()     // Catch: java.lang.Throwable -> L49
                r5.closeWithError(r6)     // Catch: java.lang.Throwable -> L49
                android.os.ParcelFileDescriptor r5 = r12.mAudioSink     // Catch: java.lang.Throwable -> L49
                java.lang.String r6 = r4.getMessage()     // Catch: java.lang.Throwable -> L49
                r5.closeWithError(r6)     // Catch: java.lang.Throwable -> L49
                java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L49
                r5.<init>()     // Catch: java.lang.Throwable -> L49
                java.lang.String r6 = r12.mStreamTaskId     // Catch: java.lang.Throwable -> L49
                r5.append(r6)     // Catch: java.lang.Throwable -> L49
                java.lang.String r6 = ": Failed to copy audio stream"
                r5.append(r6)     // Catch: java.lang.Throwable -> L49
                java.lang.String r5 = r5.toString()     // Catch: java.lang.Throwable -> L49
                android.util.Slog.i(r0, r5, r4)     // Catch: java.lang.Throwable -> L49
                int r6 = r12.mDetectorType     // Catch: java.lang.Throwable -> L49
                int r8 = r12.mUid     // Catch: java.lang.Throwable -> L49
                r7 = 10
                r9 = 0
                r10 = 0
                r11 = 0
                com.android.server.voiceinteraction.HotwordMetricsLogger.writeAudioEgressEvent(r6, r7, r8, r9, r10, r11)     // Catch: java.lang.Throwable -> L49
                if (r2 == 0) goto Lc3
                r2.close()
            Lc3:
                if (r3 == 0) goto Lc6
                goto L6e
            Lc6:
                return r1
            Lc7:
                if (r1 == 0) goto Lcc
                r1.close()
            Lcc:
                if (r3 == 0) goto Ld1
                r3.close()
            Ld1:
                throw r12
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.voiceinteraction.HotwordAudioStreamCopier.SingleAudioStreamCopyTask.call():java.lang.Object");
        }
    }

    public HotwordAudioStreamCopier(
            AppOpsManager appOpsManager, int i, int i2, String str, String str2) {
        this.mAppOpsManager = appOpsManager;
        this.mDetectorType = i;
        this.mVoiceInteractorUid = i2;
        this.mVoiceInteractorPackageName = str;
        this.mVoiceInteractorAttributionTag = str2;
    }

    public final HotwordDetectedResult startCopyingAudioStreams(
            HotwordDetectedResult hotwordDetectedResult, boolean z) {
        int i;
        List<HotwordAudioStream> audioStreams = hotwordDetectedResult.getAudioStreams();
        if (audioStreams.isEmpty()) {
            HotwordMetricsLogger.writeAudioEgressEvent(
                    this.mDetectorType, 7, this.mVoiceInteractorUid, 0, 0, 0);
            return hotwordDetectedResult;
        }
        int size = audioStreams.size();
        ArrayList arrayList = new ArrayList(audioStreams.size());
        ArrayList arrayList2 = new ArrayList(audioStreams.size());
        char c = 0;
        int i2 = 0;
        int i3 = 0;
        for (HotwordAudioStream hotwordAudioStream : audioStreams) {
            ParcelFileDescriptor[] createReliablePipe = ParcelFileDescriptor.createReliablePipe();
            ParcelFileDescriptor parcelFileDescriptor = createReliablePipe[c];
            ParcelFileDescriptor parcelFileDescriptor2 = createReliablePipe[1];
            arrayList.add(
                    hotwordAudioStream
                            .buildUpon()
                            .setAudioStreamParcelFileDescriptor(parcelFileDescriptor)
                            .build());
            PersistableBundle metadata = hotwordAudioStream.getMetadata();
            int parcelableSize = HotwordDetectedResult.getParcelableSize(metadata) + i2;
            if (metadata.containsKey(
                    "android.service.voice.key.AUDIO_STREAM_COPY_BUFFER_LENGTH_BYTES")) {
                i =
                        metadata.getInt(
                                "android.service.voice.key.AUDIO_STREAM_COPY_BUFFER_LENGTH_BYTES",
                                -1);
                if (i < 1 || i > 65536) {
                    HotwordMetricsLogger.writeAudioEgressEvent(
                            this.mDetectorType, 9, this.mVoiceInteractorUid, 0, 0, size);
                    Slog.w(
                            "HotwordAudioStreamCopier",
                            "Attempted to set an invalid copy buffer length ("
                                    + i
                                    + ") for: "
                                    + hotwordAudioStream);
                } else {
                    i3 += hotwordAudioStream.getInitialAudio().length;
                    arrayList2.add(
                            new CopyTaskInfo(
                                    hotwordAudioStream.getAudioStreamParcelFileDescriptor(),
                                    parcelFileDescriptor2,
                                    i));
                    i2 = parcelableSize;
                    c = 0;
                }
            }
            i = 32768;
            i3 += hotwordAudioStream.getInitialAudio().length;
            arrayList2.add(
                    new CopyTaskInfo(
                            hotwordAudioStream.getAudioStreamParcelFileDescriptor(),
                            parcelFileDescriptor2,
                            i));
            i2 = parcelableSize;
            c = 0;
        }
        this.mExecutorService.execute(
                new HotwordDetectedResultCopyTask(
                        "HotwordDetectedResult@" + System.identityHashCode(hotwordDetectedResult),
                        arrayList2,
                        i2,
                        i3,
                        z));
        return hotwordDetectedResult.buildUpon().setAudioStreams(arrayList).build();
    }
}
