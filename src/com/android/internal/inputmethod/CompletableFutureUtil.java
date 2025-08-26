package com.android.internal.inputmethod;

import android.util.Log;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* loaded from: classes5.dex */
public final class CompletableFutureUtil {
    private CompletableFutureUtil() {
    }

    private static <T> T getValueOrRethrowErrorInternal(CompletableFuture<T> completableFuture) {
        boolean z = false;
        while (true) {
            try {
                try {
                    break;
                } catch (InterruptedException unused) {
                    z = true;
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    throw new RuntimeException(cause.getMessage(), cause.getCause());
                }
            } finally {
                if (z) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return completableFuture.get();
    }

    private static <T> T getValueOrNullInternal(CompletableFuture<T> completableFuture, String str, String str2, long j, CancellationGroup cancellationGroup) {
        T t;
        boolean z = false;
        boolean z2 = cancellationGroup != null && cancellationGroup.tryRegisterFutureOrCancelImmediately(completableFuture);
        while (true) {
            try {
                try {
                    try {
                        try {
                            t = completableFuture.get(j, TimeUnit.MILLISECONDS);
                            break;
                        } catch (CancellationException unused) {
                            logCancellationInternal(str, str2);
                            if (z2) {
                                cancellationGroup.unregisterFuture(completableFuture);
                            }
                            if (z) {
                                Thread.currentThread().interrupt();
                            }
                            return null;
                        }
                    } catch (TimeoutException unused2) {
                        logTimeoutInternal(str, str2, j);
                        if (z2) {
                            cancellationGroup.unregisterFuture(completableFuture);
                        }
                        if (z) {
                            Thread.currentThread().interrupt();
                        }
                        return null;
                    } catch (Throwable th) {
                        logErrorInternal(str, str2, th.getMessage());
                        if (z2) {
                            cancellationGroup.unregisterFuture(completableFuture);
                        }
                        if (z) {
                            Thread.currentThread().interrupt();
                        }
                        return null;
                    }
                } catch (InterruptedException unused3) {
                    z = true;
                } catch (CompletionException e) {
                    if (e.getCause() instanceof CancellationException) {
                        logCancellationInternal(str, str2);
                        if (z2) {
                            cancellationGroup.unregisterFuture(completableFuture);
                        }
                        if (z) {
                            Thread.currentThread().interrupt();
                        }
                        return null;
                    }
                    logErrorInternal(str, str2, e.getMessage());
                    if (z2) {
                        cancellationGroup.unregisterFuture(completableFuture);
                    }
                    if (z) {
                        Thread.currentThread().interrupt();
                    }
                    return null;
                }
            } catch (Throwable th2) {
                if (z2) {
                    cancellationGroup.unregisterFuture(completableFuture);
                }
                if (z) {
                    Thread.currentThread().interrupt();
                }
                throw th2;
            }
        }
        if (z2) {
            cancellationGroup.unregisterFuture(completableFuture);
        }
        if (z) {
            Thread.currentThread().interrupt();
        }
        return t;
    }

    private static void logTimeoutInternal(String str, String str2, long j) {
        if (str == null || str2 == null) {
            return;
        }
        Log.w(str, str2 + " didn't respond in " + j + " msec.");
    }

    private static void logErrorInternal(String str, String str2, String str3) {
        if (str == null || str2 == null) {
            return;
        }
        Log.w(str, str2 + " was failed with an exception=" + str3);
    }

    private static void logCancellationInternal(String str, String str2) {
        if (str == null || str2 == null) {
            return;
        }
        Log.w(str, str2 + " was cancelled.");
    }

    public static <T> T getResult(CompletableFuture<T> completableFuture) {
        return (T) getValueOrRethrowErrorInternal(completableFuture);
    }

    public static boolean getBooleanResult(CompletableFuture<Boolean> completableFuture) {
        return ((Boolean) getValueOrRethrowErrorInternal(completableFuture)).booleanValue();
    }

    public static int getIntegerResult(CompletableFuture<Integer> completableFuture) {
        return ((Integer) getValueOrRethrowErrorInternal(completableFuture)).intValue();
    }

    public static boolean getResultOrFalse(CompletableFuture<Boolean> completableFuture, String str, String str2, CancellationGroup cancellationGroup, long j) {
        Boolean bool = (Boolean) getValueOrNullInternal(completableFuture, str, str2, j, cancellationGroup);
        if (bool != null) {
            return bool.booleanValue();
        }
        return false;
    }

    public static int getResultOrZero(CompletableFuture<Integer> completableFuture, String str, String str2, CancellationGroup cancellationGroup, long j) {
        Integer num = (Integer) getValueOrNullInternal(completableFuture, str, str2, j, cancellationGroup);
        if (num != null) {
            return num.intValue();
        }
        return 0;
    }

    public static <T> T getResultOrNull(CompletableFuture<T> completableFuture, String str, String str2, CancellationGroup cancellationGroup, long j) {
        return (T) getValueOrNullInternal(completableFuture, str, str2, j, cancellationGroup);
    }
}
