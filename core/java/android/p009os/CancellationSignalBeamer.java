package android.p009os;

import android.p009os.CancellationSignal;
import android.p009os.CancellationSignalBeamer;
import android.p009os.IBinder;
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

        public CloseableToken beam(CancellationSignal cs) {
            if (cs == null) {
                return null;
            }
            return new Token(cs);
        }

        public MustClose beamScopeIfNeeded(HandwritingGesture gesture) {
            if (!(gesture instanceof CancellableHandwritingGesture)) {
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
            ArrayList<CloseableToken> tokens = threadLocal.get().second;
            threadLocal.remove();
            for (int i = tokens.size() - 1; i >= 0; i--) {
                if (tokens.get(i) != null) {
                    tokens.get(i).close();
                }
            }
        }

        public static IBinder beamFromScope(CancellationSignal cs) {
            Pair<Sender, ArrayList<CloseableToken>> state = sScope.get();
            if (state != null) {
                CloseableToken token = state.first.beam(cs);
                state.second.add(token);
                return token;
            }
            return null;
        }

        private static class Token extends Binder implements CloseableToken, Runnable {
            private Preparer mPreparer;
            private final Sender mSender;

            private Token(Sender sender, CancellationSignal signal) {
                this.mSender = sender;
                this.mPreparer = new Preparer(sender, signal, this);
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

                private Preparer(Sender sender, CancellationSignal signal, Token token) {
                    this.mSender = sender;
                    this.mSignal = signal;
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

        public Receiver(boolean cancelOnSenderDeath) {
            this.mCancelOnSenderDeath = cancelOnSenderDeath;
        }

        public CancellationSignal unbeam(IBinder token) {
            if (token == null) {
                return null;
            }
            synchronized (this) {
                CancellationSignal cs = this.mTokenMap.get(token);
                if (cs != null) {
                    return cs;
                }
                CancellationSignal cs2 = new CancellationSignal();
                this.mTokenMap.put(token, cs2);
                try {
                    token.linkToDeath(this, 0);
                } catch (RemoteException e) {
                    dead(token);
                }
                return cs2;
            }
        }

        public void forget(IBinder token) {
            synchronized (this) {
                if (this.mTokenMap.remove(token) != null) {
                    token.unlinkToDeath(this, 0);
                }
            }
        }

        public void cancel(IBinder token) {
            synchronized (this) {
                CancellationSignal cs = this.mTokenMap.get(token);
                if (cs != null) {
                    forget(token);
                    cs.cancel();
                }
            }
        }

        private void dead(IBinder token) {
            if (this.mCancelOnSenderDeath) {
                cancel(token);
            } else {
                forget(token);
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied(IBinder who) {
            dead(who);
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            throw new RuntimeException("unreachable");
        }
    }
}
