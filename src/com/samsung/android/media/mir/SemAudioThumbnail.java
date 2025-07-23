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
        } catch (Exception unused) {
            isNativeLibraryReady = false;
        } catch (UnsatisfiedLinkError unused2) {
            isNativeLibraryReady = false;
        }
    }

    public boolean checkFile(String str) {
        if (isNativeLibraryReady && str != null) {
            try {
                int init = init(str, 0);
                if (init >= 0) {
                    deinit(init);
                    return true;
                }
            } catch (Exception | UnsatisfiedLinkError unused) {
            }
        }
        return false;
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.samsung.android.media.mir.SemAudioThumbnail$1] */
    public void extract(String str, int i, ResultListener resultListener) {
        if (resultListener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (str == null) {
            sendErrorMessage(resultListener, -7);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(resultListener, -1);
            return;
        }
        if (i < 0) {
            sendErrorMessage(resultListener, -4);
            return;
        }
        try {
            int init = init(str, i);
            this.mHandle = init;
            this.mListener = resultListener;
            if (init >= 0) {
                if (extract(init) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            boolean z = false;
                            int i2 = -1;
                            while (!z) {
                                try {
                                    try {
                                        sleep(100L);
                                        SemAudioThumbnail semAudioThumbnail = SemAudioThumbnail.this;
                                        int stat = semAudioThumbnail.getStat(semAudioThumbnail.mHandle);
                                        if (i2 != stat) {
                                            if (stat == -4 || stat == -1) {
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(-1L);
                                                }
                                            } else if (stat == 5) {
                                                SemAudioThumbnail semAudioThumbnail2 = SemAudioThumbnail.this;
                                                long info = semAudioThumbnail2.getInfo(semAudioThumbnail2.mHandle);
                                                SemAudioThumbnail semAudioThumbnail3 = SemAudioThumbnail.this;
                                                semAudioThumbnail3.deinit(semAudioThumbnail3.mHandle);
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(info);
                                                }
                                            } else if (stat != 6) {
                                                i2 = stat;
                                            } else {
                                                SemAudioThumbnail semAudioThumbnail4 = SemAudioThumbnail.this;
                                                semAudioThumbnail4.deinit(semAudioThumbnail4.mHandle);
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(0L);
                                                }
                                            }
                                            i2 = stat;
                                            z = true;
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        SemAudioThumbnail semAudioThumbnail5 = SemAudioThumbnail.this;
                                        semAudioThumbnail5.deinit(semAudioThumbnail5.mHandle);
                                        return;
                                    }
                                } catch (NullPointerException | Exception unused) {
                                    return;
                                }
                            }
                        }
                    }.start();
                    return;
                } else {
                    sendErrorMessage(resultListener, -1);
                    return;
                }
            }
            this.lastError = init;
            if (init == -7) {
                sendErrorMessage(resultListener, -7);
            } else if (init == -3) {
                sendErrorMessage(resultListener, -3);
            } else {
                sendErrorMessage(resultListener, -1);
            }
        } catch (Exception unused) {
        } catch (UnsatisfiedLinkError unused2) {
            sendErrorMessage(resultListener, -1);
        }
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.samsung.android.media.mir.SemAudioThumbnail$2] */
    public void extract(String str, ResultListener resultListener) {
        if (resultListener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (str == null) {
            sendErrorMessage(resultListener, -7);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(resultListener, -1);
            return;
        }
        try {
            int init = init(str, 0);
            this.mHandle = init;
            this.mListener = resultListener;
            if (init >= 0) {
                if (extract(init) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.2
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            boolean z = false;
                            int i = -1;
                            while (!z) {
                                try {
                                    try {
                                        sleep(100L);
                                        SemAudioThumbnail semAudioThumbnail = SemAudioThumbnail.this;
                                        int stat = semAudioThumbnail.getStat(semAudioThumbnail.mHandle);
                                        if (i != stat) {
                                            if (stat == -4 || stat == -1) {
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(-1L);
                                                }
                                            } else if (stat == 5) {
                                                SemAudioThumbnail semAudioThumbnail2 = SemAudioThumbnail.this;
                                                long info = semAudioThumbnail2.getInfo(semAudioThumbnail2.mHandle);
                                                SemAudioThumbnail semAudioThumbnail3 = SemAudioThumbnail.this;
                                                semAudioThumbnail3.deinit(semAudioThumbnail3.mHandle);
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(info);
                                                }
                                            } else if (stat != 6) {
                                                i = stat;
                                            } else {
                                                SemAudioThumbnail semAudioThumbnail4 = SemAudioThumbnail.this;
                                                semAudioThumbnail4.deinit(semAudioThumbnail4.mHandle);
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(0L);
                                                }
                                            }
                                            i = stat;
                                            z = true;
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        SemAudioThumbnail semAudioThumbnail5 = SemAudioThumbnail.this;
                                        semAudioThumbnail5.deinit(semAudioThumbnail5.mHandle);
                                        return;
                                    }
                                } catch (NullPointerException | Exception unused) {
                                    return;
                                }
                            }
                        }
                    }.start();
                    return;
                } else {
                    sendErrorMessage(resultListener, -1);
                    return;
                }
            }
            this.lastError = init;
            if (init == -7) {
                sendErrorMessage(resultListener, -7);
            } else if (init == -3) {
                sendErrorMessage(resultListener, -3);
            } else {
                sendErrorMessage(resultListener, -1);
            }
        } catch (Exception unused) {
        } catch (UnsatisfiedLinkError unused2) {
            sendErrorMessage(resultListener, -1);
        }
    }

    /* JADX WARN: Type inference failed for: r4v4, types: [com.samsung.android.media.mir.SemAudioThumbnail$3] */
    public void extract(FileDescriptor fileDescriptor, ResultListener resultListener) {
        if (resultListener == null) {
            throw new RuntimeException("listener is null.");
        }
        if (fileDescriptor == null) {
            sendErrorMessage(resultListener, -4);
            return;
        }
        if (!isNativeLibraryReady) {
            sendErrorMessage(resultListener, -1);
            return;
        }
        try {
            int initialize = initialize(fileDescriptor, 0);
            this.mHandle = initialize;
            this.mListener = resultListener;
            if (initialize >= 0) {
                if (extract(initialize) == 0) {
                    new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.3
                        @Override // java.lang.Thread, java.lang.Runnable
                        public void run() {
                            boolean z = false;
                            int i = -1;
                            while (!z) {
                                try {
                                    try {
                                        sleep(100L);
                                        SemAudioThumbnail semAudioThumbnail = SemAudioThumbnail.this;
                                        int stat = semAudioThumbnail.getStat(semAudioThumbnail.mHandle);
                                        if (i != stat) {
                                            if (stat == -4 || stat == -1) {
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(-1L);
                                                }
                                            } else if (stat == 5) {
                                                SemAudioThumbnail semAudioThumbnail2 = SemAudioThumbnail.this;
                                                long info = semAudioThumbnail2.getInfo(semAudioThumbnail2.mHandle);
                                                SemAudioThumbnail semAudioThumbnail3 = SemAudioThumbnail.this;
                                                semAudioThumbnail3.deinit(semAudioThumbnail3.mHandle);
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(info);
                                                }
                                            } else if (stat != 6) {
                                                i = stat;
                                            } else {
                                                SemAudioThumbnail semAudioThumbnail4 = SemAudioThumbnail.this;
                                                semAudioThumbnail4.deinit(semAudioThumbnail4.mHandle);
                                                if (SemAudioThumbnail.this.mListener != null) {
                                                    SemAudioThumbnail.this.mListener.onDone(0L);
                                                }
                                            }
                                            i = stat;
                                            z = true;
                                        }
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                        SemAudioThumbnail semAudioThumbnail5 = SemAudioThumbnail.this;
                                        semAudioThumbnail5.deinit(semAudioThumbnail5.mHandle);
                                        return;
                                    }
                                } catch (NullPointerException | Exception unused) {
                                    return;
                                }
                            }
                        }
                    }.start();
                    return;
                } else {
                    sendErrorMessage(resultListener, -1);
                    return;
                }
            }
            this.lastError = initialize;
            if (initialize == -7) {
                sendErrorMessage(resultListener, -4);
            } else if (initialize == -3) {
                sendErrorMessage(resultListener, -3);
            } else {
                sendErrorMessage(resultListener, -1);
            }
        } catch (Exception unused) {
        } catch (UnsatisfiedLinkError unused2) {
            sendErrorMessage(resultListener, -1);
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.media.mir.SemAudioThumbnail$4] */
    private void sendErrorMessage(ResultListener resultListener, int i) {
        this.mListener = resultListener;
        this.lastError = i;
        new Thread("SemAudioThumbnail thread") { // from class: com.samsung.android.media.mir.SemAudioThumbnail.4
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                try {
                    if (SemAudioThumbnail.this.mListener != null) {
                        SemAudioThumbnail.this.mListener.onError(SemAudioThumbnail.this.lastError);
                    }
                } catch (NullPointerException | Exception unused) {
                }
            }
        }.start();
    }
}
