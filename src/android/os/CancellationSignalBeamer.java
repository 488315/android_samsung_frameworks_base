package android.os;

import android.os.CancellationSignal;
import android.os.CancellationSignalBeamer;
import android.os.IBinder;
import android.system.SystemCleaner;
import android.util.Pair;
import android.view.inputmethod.CancellableHandwritingGesture;
import android.view.inputmethod.HandwritingGesture;
import java.lang.ref.Cleaner;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class CancellationSignalBeamer {
    static final Cleaner sCleaner = SystemCleaner.cleaner();

    public static abstract class Sender {
        private static final ThreadLocal<Pair<Sender, ArrayList<CloseableToken>>> sScope = new ThreadLocal<>();

        public interface CloseableToken extends IBinder, MustClose {
            @Override // android.os.CancellationSignalBeamer.Sender.MustClose, java.lang.AutoCloseable
            void close();
        }

        public interface MustClose extends AutoCloseable {
            @Override // java.lang.AutoCloseable
            void close();
        }

        public abstract void onCancel(IBinder iBinder);

        public abstract void onForget(IBinder iBinder);

        public CloseableToken beam(CancellationSignal cancellationSignal) {
            if (cancellationSignal == null) {
                return null;
            }
            return new Token(cancellationSignal);
        }

        public MustClose beamScopeIfNeeded(HandwritingGesture handwritingGesture) {
            if (!(handwritingGesture instanceof CancellableHandwritingGesture)) {
                return null;
            }
            sScope.set(Pair.create(this, new ArrayList()));
            return new MustClose() { // from class: android.os.CancellationSignalBeamer$Sender$$ExternalSyntheticLambda0
                @Override // android.os.CancellationSignalBeamer.Sender.MustClose, java.lang.AutoCloseable
                public final void close() {
                    CancellationSignalBeamer.Sender.lambda$beamScopeIfNeeded$0();
                }
            };
        }

        static /* synthetic */ void lambda$beamScopeIfNeeded$0() {
            ThreadLocal<Pair<Sender, ArrayList<CloseableToken>>> threadLocal = sScope;
            ArrayList<CloseableToken> arrayList = threadLocal.get().second;
            threadLocal.remove();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (arrayList.get(size) != null) {
                    arrayList.get(size).close();
                }
            }
        }

        public static IBinder beamFromScope(CancellationSignal cancellationSignal) {
            Pair<Sender, ArrayList<CloseableToken>> pair = sScope.get();
            if (pair == null) {
                return null;
            }
            CloseableToken closeableTokenBeam = pair.first.beam(cancellationSignal);
            pair.second.add(closeableTokenBeam);
            return closeableTokenBeam;
        }

        private static class Token extends Binder implements CloseableToken, Runnable {
            private Preparer mPreparer;
            private final Sender mSender;

            private Token(Sender sender, CancellationSignal cancellationSignal) {
                this.mSender = sender;
                this.mPreparer = new Preparer(sender, cancellationSignal, this);
            }

            @Override // android.os.CancellationSignalBeamer.Sender.CloseableToken, android.os.CancellationSignalBeamer.Sender.MustClose, java.lang.AutoCloseable
            public void close() {
                Preparer preparer = this.mPreparer;
                this.mPreparer = null;
                if (preparer != null) {
                    preparer.setup();
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                this.mSender.onForget(this);
            }

            private static class Preparer implements CancellationSignal.OnCancelListener {
                private final Sender mSender;
                private final CancellationSignal mSignal;
                private final Token mToken;

                private Preparer(Sender sender, CancellationSignal cancellationSignal, Token token) {
                    this.mSender = sender;
                    this.mSignal = cancellationSignal;
                    this.mToken = token;
                }

                void setup() {
                    CancellationSignalBeamer.sCleaner.register(this, this.mToken);
                    this.mSignal.setOnCancelListener(this);
                }

                @Override // android.os.CancellationSignal.OnCancelListener
                public void onCancel() {
                    try {
                        this.mSender.onCancel(this.mToken);
                    } finally {
                        Reference.reachabilityFence(this);
                    }
                }
            }
        }
    }

    public static class Receiver implements IBinder.DeathRecipient {
        private final boolean mCancelOnSenderDeath;
        private final HashMap<IBinder, CancellationSignal> mTokenMap = new HashMap<>();

        public Receiver(boolean z) {
            this.mCancelOnSenderDeath = z;
        }

        public CancellationSignal unbeam(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            synchronized (this) {
                CancellationSignal cancellationSignal = this.mTokenMap.get(iBinder);
                if (cancellationSignal != null) {
                    return cancellationSignal;
                }
                CancellationSignal cancellationSignal2 = new CancellationSignal();
                this.mTokenMap.put(iBinder, cancellationSignal2);
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    dead(iBinder);
                }
                return cancellationSignal2;
            }
        }

        public void forget(IBinder iBinder) {
            synchronized (this) {
                if (this.mTokenMap.remove(iBinder) != null) {
                    iBinder.unlinkToDeath(this, 0);
                }
            }
        }

        public void cancel(IBinder iBinder) {
            synchronized (this) {
                CancellationSignal cancellationSignal = this.mTokenMap.get(iBinder);
                if (cancellationSignal != null) {
                    forget(iBinder);
                    cancellationSignal.cancel();
                }
            }
        }

        private void dead(IBinder iBinder) {
            if (this.mCancelOnSenderDeath) {
                cancel(iBinder);
            } else {
                forget(iBinder);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(IBinder iBinder) {
            dead(iBinder);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            throw new RuntimeException("unreachable");
        }
    }
}
