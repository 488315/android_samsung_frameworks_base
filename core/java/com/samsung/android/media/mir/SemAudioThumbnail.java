package com.samsung.android.media.mir;

import java.io.FileDescriptor;

/* loaded from: classes6.dex */
public class SemAudioThumbnail {
    public static final int ERROR_INVALID_ARG = -4;
    public static final int ERROR_INVALID_PATH = -7;
    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_UNSUPPORTED = -3;
    private static final int SMAT_ERR = -1;
    private static final int SMAT_ERR_INSUFF_MEM = -2;
    private static final int SMAT_ERR_INVALID_ARG = -4;
    private static final int SMAT_ERR_NOT_OPEN_FILE = -7;
    private static final int SMAT_ERR_UNSUPPORT = -3;
    private static final int SMAT_EXTRACT_DONE = 5;
    private static final int SMAT_OK = 0;
    private static final int SMAT_QUIT_DONE = 6;
    private static final int SMAT_READY = 1;
    private static boolean isNativeLibraryReady = false;
    private ResultListener mListener = null;
    private int lastError = -1;
    private int mHandle = -1;

    public interface ResultListener {
        void onDone(long j);

        void onError(int i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native int deinit(int i);

    private native int extract(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native long getInfo(int i);

    /* JADX INFO: Access modifiers changed from: private */
    public native int getStat(int i);

    private native int init(String str, int i);

    private native int initialize(FileDescriptor fileDescriptor, int i);

    public SemAudioThumbnail() {
        try {
            System.loadLibrary("smat");
            isNativeLibraryReady = true;
        } catch (Exception e) {
            isNativeLibraryReady = false;
        } catch (UnsatisfiedLinkError e2) {
            isNativeLibraryReady = false;
        }
    }

    public boolean checkFile(String path) {
        if (!isNativeLibraryReady || path == null) {
            return false;
        }
        try {
            int handle = init(path, 0);
            if (handle < 0) {
                return false;
            }
            deinit(handle);
            return true;
        } catch (Exception e) {
            return false;
        } catch (UnsatisfiedLinkError e2) {
            return false;
        }
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.media.mir.SemAudioThumbnail$1] */
    public void extract(String path, int duration, ResultListener listener) {
        if (listener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (path == null) {
            sendErrorMessage(listener, -7);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(listener, -1);
            return;
        }
        if (duration < 0) {
            sendErrorMessage(listener, -4);
            return;
        }
        try {
            this.mHandle = init(path, duration);
            this.mListener = listener;
            if (this.mHandle >= 0) {
                if (extract(this.mHandle) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.1
                        /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:
                        
                            continue;
                         */
                        @Override // java.lang.Thread, java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void run() {
                            /*
                                r7 = this;
                                r0 = -1
                                r1 = -1
                                r2 = 0
                            L3:
                                if (r2 != 0) goto L95
                                r3 = 100
                                sleep(r3)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8941$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                r1 = r3
                                if (r0 == r1) goto L82
                                r0 = r1
                                switch(r1) {
                                    case -4: goto L69;
                                    case -1: goto L69;
                                    case 5: goto L3f;
                                    case 6: goto L1f;
                                    default: goto L1e;
                                }
                            L1e:
                                goto L82
                            L1f:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r3 == 0) goto L3d
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r4 = 0
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                            L3d:
                                r2 = 1
                                goto L82
                            L3f:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8940$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r6 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r6)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r5 == 0) goto L67
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r5.onDone(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                            L67:
                                r2 = 1
                                goto L82
                            L69:
                                r2 = 1
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r3 == 0) goto L82
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r4 = -1
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                goto L82
                            L7e:
                                r3 = move-exception
                                goto L95
                            L80:
                                r3 = move-exception
                                goto L95
                            L82:
                                goto L3
                            L83:
                                r3 = move-exception
                                goto L95
                            L85:
                                r3 = move-exception
                                r3.printStackTrace()
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r5)
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r4, r5)
                            L95:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass1.run():void");
                        }
                    }.start();
                } else {
                    sendErrorMessage(listener, -1);
                }
            } else {
                this.lastError = this.mHandle;
                switch (this.lastError) {
                    case -7:
                        sendErrorMessage(listener, -7);
                        break;
                    case -3:
                        sendErrorMessage(listener, -3);
                        break;
                    default:
                        sendErrorMessage(listener, -1);
                        break;
                }
            }
        } catch (Exception e) {
        } catch (UnsatisfiedLinkError e2) {
            sendErrorMessage(listener, -1);
        }
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.media.mir.SemAudioThumbnail$2] */
    public void extract(String path, ResultListener listener) {
        if (listener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (path == null) {
            sendErrorMessage(listener, -7);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(listener, -1);
            return;
        }
        try {
            this.mHandle = init(path, 0);
            this.mListener = listener;
            if (this.mHandle >= 0) {
                if (extract(this.mHandle) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.2
                        /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:
                        
                            continue;
                         */
                        @Override // java.lang.Thread, java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void run() {
                            /*
                                r7 = this;
                                r0 = -1
                                r1 = -1
                                r2 = 0
                            L3:
                                if (r2 != 0) goto L95
                                r3 = 100
                                sleep(r3)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8941$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                r1 = r3
                                if (r0 == r1) goto L82
                                r0 = r1
                                switch(r1) {
                                    case -4: goto L69;
                                    case -1: goto L69;
                                    case 5: goto L3f;
                                    case 6: goto L1f;
                                    default: goto L1e;
                                }
                            L1e:
                                goto L82
                            L1f:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r3 == 0) goto L3d
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r4 = 0
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                            L3d:
                                r2 = 1
                                goto L82
                            L3f:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8940$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r6 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r6)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r5 == 0) goto L67
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r5.onDone(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                            L67:
                                r2 = 1
                                goto L82
                            L69:
                                r2 = 1
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r3 == 0) goto L82
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r4 = -1
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                goto L82
                            L7e:
                                r3 = move-exception
                                goto L95
                            L80:
                                r3 = move-exception
                                goto L95
                            L82:
                                goto L3
                            L83:
                                r3 = move-exception
                                goto L95
                            L85:
                                r3 = move-exception
                                r3.printStackTrace()
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r5)
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r4, r5)
                            L95:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass2.run():void");
                        }
                    }.start();
                } else {
                    sendErrorMessage(listener, -1);
                }
            } else {
                this.lastError = this.mHandle;
                switch (this.lastError) {
                    case -7:
                        sendErrorMessage(listener, -7);
                        break;
                    case -3:
                        sendErrorMessage(listener, -3);
                        break;
                    default:
                        sendErrorMessage(listener, -1);
                        break;
                }
            }
        } catch (Exception e) {
        } catch (UnsatisfiedLinkError e2) {
            sendErrorMessage(listener, -1);
        }
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.samsung.android.media.mir.SemAudioThumbnail$3] */
    public void extract(FileDescriptor fd, ResultListener listener) {
        if (listener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (fd == null) {
            sendErrorMessage(listener, -4);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(listener, -1);
            return;
        }
        try {
            this.mHandle = initialize(fd, 0);
            this.mListener = listener;
            if (this.mHandle >= 0) {
                if (extract(this.mHandle) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.3
                        /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:
                        
                            continue;
                         */
                        @Override // java.lang.Thread, java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public void run() {
                            /*
                                r7 = this;
                                r0 = -1
                                r1 = -1
                                r2 = 0
                            L3:
                                if (r2 != 0) goto L95
                                r3 = 100
                                sleep(r3)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                int r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8941$$Nest$mgetStat(r3, r4)     // Catch: java.lang.Exception -> L83 java.lang.InterruptedException -> L85
                                r1 = r3
                                if (r0 == r1) goto L82
                                r0 = r1
                                switch(r1) {
                                    case -4: goto L69;
                                    case -1: goto L69;
                                    case 5: goto L3f;
                                    case 6: goto L1f;
                                    default: goto L1e;
                                }
                            L1e:
                                goto L82
                            L1f:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r3, r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r3 == 0) goto L3d
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r4 = 0
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                            L3d:
                                r2 = 1
                                goto L82
                            L3f:
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r4 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                long r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8940$$Nest$mgetInfo(r3, r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r6 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                int r6 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r6)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r5, r6)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r5 == 0) goto L67
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r5)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r5.onDone(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                            L67:
                                r2 = 1
                                goto L82
                            L69:
                                r2 = 1
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                if (r3 == 0) goto L82
                                com.samsung.android.media.mir.SemAudioThumbnail r3 = com.samsung.android.media.mir.SemAudioThumbnail.this     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                com.samsung.android.media.mir.SemAudioThumbnail$ResultListener r3 = com.samsung.android.media.mir.SemAudioThumbnail.m8938$$Nest$fgetmListener(r3)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                r4 = -1
                                r3.onDone(r4)     // Catch: java.lang.Exception -> L7e java.lang.NullPointerException -> L80
                                goto L82
                            L7e:
                                r3 = move-exception
                                goto L95
                            L80:
                                r3 = move-exception
                                goto L95
                            L82:
                                goto L3
                            L83:
                                r3 = move-exception
                                goto L95
                            L85:
                                r3 = move-exception
                                r3.printStackTrace()
                                com.samsung.android.media.mir.SemAudioThumbnail r4 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                com.samsung.android.media.mir.SemAudioThumbnail r5 = com.samsung.android.media.mir.SemAudioThumbnail.this
                                int r5 = com.samsung.android.media.mir.SemAudioThumbnail.m8937$$Nest$fgetmHandle(r5)
                                com.samsung.android.media.mir.SemAudioThumbnail.m8939$$Nest$mdeinit(r4, r5)
                            L95:
                                return
                            */
                            throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.media.mir.SemAudioThumbnail.AnonymousClass3.run():void");
                        }
                    }.start();
                } else {
                    sendErrorMessage(listener, -1);
                }
            } else {
                this.lastError = this.mHandle;
                switch (this.lastError) {
                    case -7:
                        sendErrorMessage(listener, -4);
                        break;
                    case -3:
                        sendErrorMessage(listener, -3);
                        break;
                    default:
                        sendErrorMessage(listener, -1);
                        break;
                }
            }
        } catch (Exception e) {
        } catch (UnsatisfiedLinkError e2) {
            sendErrorMessage(listener, -1);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.samsung.android.media.mir.SemAudioThumbnail$4] */
    private void sendErrorMessage(ResultListener listener, int errorType) {
        this.mListener = listener;
        this.lastError = errorType;
        new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    if (SemAudioThumbnail.this.mListener != null) {
                        SemAudioThumbnail.this.mListener.onError(SemAudioThumbnail.this.lastError);
                    }
                } catch (NullPointerException e) {
                } catch (Exception e2) {
                }
            }
        }.start();
    }
}
