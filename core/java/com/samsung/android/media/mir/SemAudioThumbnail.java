package com.samsung.android.media.mir;

import java.io.FileDescriptor;

/* loaded from: classes5.dex */
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

  /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.media.mir.SemAudioThumbnail$1] */
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
      int init = init(path, duration);
      this.mHandle = init;
      this.mListener = listener;
      if (init >= 0) {
        if (extract(init) == 0) {
          new Thread(
              "SemAudioThumbnail thread") { // from class:
                                            // com.samsung.android.media.mir.SemAudioThumbnail.1
            /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:

               continue;
            */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
              int oldState = -1;
              boolean isDone = false;
              while (!isDone) {
                try {
                  sleep(100L);
                  SemAudioThumbnail semAudioThumbnail = SemAudioThumbnail.this;
                  int newState = semAudioThumbnail.getStat(semAudioThumbnail.mHandle);
                  if (oldState != newState) {
                    oldState = newState;
                    switch (newState) {
                      case -4:
                      case -1:
                        isDone = true;
                        if (SemAudioThumbnail.this.mListener == null) {
                          break;
                        } else {
                          SemAudioThumbnail.this.mListener.onDone(-1L);
                          break;
                        }
                      case 5:
                        SemAudioThumbnail semAudioThumbnail2 = SemAudioThumbnail.this;
                        long resultms = semAudioThumbnail2.getInfo(semAudioThumbnail2.mHandle);
                        SemAudioThumbnail semAudioThumbnail3 = SemAudioThumbnail.this;
                        semAudioThumbnail3.deinit(semAudioThumbnail3.mHandle);
                        if (SemAudioThumbnail.this.mListener != null) {
                          SemAudioThumbnail.this.mListener.onDone(resultms);
                        }
                        isDone = true;
                        break;
                      case 6:
                        try {
                          SemAudioThumbnail semAudioThumbnail4 = SemAudioThumbnail.this;
                          semAudioThumbnail4.deinit(semAudioThumbnail4.mHandle);
                          if (SemAudioThumbnail.this.mListener != null) {
                            SemAudioThumbnail.this.mListener.onDone(0L);
                          }
                          isDone = true;
                          break;
                        } catch (NullPointerException e) {
                          return;
                        } catch (Exception e2) {
                          return;
                        }
                    }
                  }
                } catch (InterruptedException e3) {
                  e3.printStackTrace();
                  SemAudioThumbnail semAudioThumbnail5 = SemAudioThumbnail.this;
                  semAudioThumbnail5.deinit(semAudioThumbnail5.mHandle);
                  return;
                } catch (Exception e4) {
                  return;
                }
              }
            }
          }.start();
        } else {
          sendErrorMessage(listener, -1);
        }
      } else {
        this.lastError = init;
        switch (init) {
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

  /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.media.mir.SemAudioThumbnail$2] */
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
      int init = init(path, 0);
      this.mHandle = init;
      this.mListener = listener;
      if (init >= 0) {
        if (extract(init) == 0) {
          new Thread(
              "SemAudioThumbnail thread") { // from class:
                                            // com.samsung.android.media.mir.SemAudioThumbnail.2
            /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:

               continue;
            */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
              int oldState = -1;
              boolean isDone = false;
              while (!isDone) {
                try {
                  sleep(100L);
                  SemAudioThumbnail semAudioThumbnail = SemAudioThumbnail.this;
                  int newState = semAudioThumbnail.getStat(semAudioThumbnail.mHandle);
                  if (oldState != newState) {
                    oldState = newState;
                    switch (newState) {
                      case -4:
                      case -1:
                        isDone = true;
                        if (SemAudioThumbnail.this.mListener == null) {
                          break;
                        } else {
                          SemAudioThumbnail.this.mListener.onDone(-1L);
                          break;
                        }
                      case 5:
                        SemAudioThumbnail semAudioThumbnail2 = SemAudioThumbnail.this;
                        long resultms = semAudioThumbnail2.getInfo(semAudioThumbnail2.mHandle);
                        SemAudioThumbnail semAudioThumbnail3 = SemAudioThumbnail.this;
                        semAudioThumbnail3.deinit(semAudioThumbnail3.mHandle);
                        if (SemAudioThumbnail.this.mListener != null) {
                          SemAudioThumbnail.this.mListener.onDone(resultms);
                        }
                        isDone = true;
                        break;
                      case 6:
                        try {
                          SemAudioThumbnail semAudioThumbnail4 = SemAudioThumbnail.this;
                          semAudioThumbnail4.deinit(semAudioThumbnail4.mHandle);
                          if (SemAudioThumbnail.this.mListener != null) {
                            SemAudioThumbnail.this.mListener.onDone(0L);
                          }
                          isDone = true;
                          break;
                        } catch (NullPointerException e) {
                          return;
                        } catch (Exception e2) {
                          return;
                        }
                    }
                  }
                } catch (InterruptedException e3) {
                  e3.printStackTrace();
                  SemAudioThumbnail semAudioThumbnail5 = SemAudioThumbnail.this;
                  semAudioThumbnail5.deinit(semAudioThumbnail5.mHandle);
                  return;
                } catch (Exception e4) {
                  return;
                }
              }
            }
          }.start();
        } else {
          sendErrorMessage(listener, -1);
        }
      } else {
        this.lastError = init;
        switch (init) {
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

  /* JADX WARN: Type inference failed for: r0v6, types: [com.samsung.android.media.mir.SemAudioThumbnail$3] */
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
      int initialize = initialize(fd, 0);
      this.mHandle = initialize;
      this.mListener = listener;
      if (initialize >= 0) {
        if (extract(initialize) == 0) {
          new Thread(
              "SemAudioThumbnail thread") { // from class:
                                            // com.samsung.android.media.mir.SemAudioThumbnail.3
            /* JADX WARN: Code restructure failed: missing block: B:37:0x0003, code lost:

               continue;
            */
            @Override // java.lang.Thread, java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
              int oldState = -1;
              boolean isDone = false;
              while (!isDone) {
                try {
                  sleep(100L);
                  SemAudioThumbnail semAudioThumbnail = SemAudioThumbnail.this;
                  int newState = semAudioThumbnail.getStat(semAudioThumbnail.mHandle);
                  if (oldState != newState) {
                    oldState = newState;
                    switch (newState) {
                      case -4:
                      case -1:
                        isDone = true;
                        if (SemAudioThumbnail.this.mListener == null) {
                          break;
                        } else {
                          SemAudioThumbnail.this.mListener.onDone(-1L);
                          break;
                        }
                      case 5:
                        SemAudioThumbnail semAudioThumbnail2 = SemAudioThumbnail.this;
                        long resultms = semAudioThumbnail2.getInfo(semAudioThumbnail2.mHandle);
                        SemAudioThumbnail semAudioThumbnail3 = SemAudioThumbnail.this;
                        semAudioThumbnail3.deinit(semAudioThumbnail3.mHandle);
                        if (SemAudioThumbnail.this.mListener != null) {
                          SemAudioThumbnail.this.mListener.onDone(resultms);
                        }
                        isDone = true;
                        break;
                      case 6:
                        try {
                          SemAudioThumbnail semAudioThumbnail4 = SemAudioThumbnail.this;
                          semAudioThumbnail4.deinit(semAudioThumbnail4.mHandle);
                          if (SemAudioThumbnail.this.mListener != null) {
                            SemAudioThumbnail.this.mListener.onDone(0L);
                          }
                          isDone = true;
                          break;
                        } catch (NullPointerException e) {
                          return;
                        } catch (Exception e2) {
                          return;
                        }
                    }
                  }
                } catch (InterruptedException e3) {
                  e3.printStackTrace();
                  SemAudioThumbnail semAudioThumbnail5 = SemAudioThumbnail.this;
                  semAudioThumbnail5.deinit(semAudioThumbnail5.mHandle);
                  return;
                } catch (Exception e4) {
                  return;
                }
              }
            }
          }.start();
        } else {
          sendErrorMessage(listener, -1);
        }
      } else {
        this.lastError = initialize;
        switch (initialize) {
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
    new Thread(
        "SemAudioThumbnail thread") { // from class:
                                      // com.samsung.android.media.mir.SemAudioThumbnail.4
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
