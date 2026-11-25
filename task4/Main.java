import java.io.*;
import java.util.*;

/**
 * КТ_2, задача 4. Минимальная вместимость корабля.
 * Идея: бинарный поиск по ответу. Для вместимости cap жадно считаем, сколько дней нужно
 * (грузим подряд, пока влезает). Функция «хватает ли days дней» монотонна по cap.
 * Границы: от max(weights) до sum(weights). Время O(n log(sum)).
 */
public class Main {
    static int daysNeeded(int[] w, long cap) {
        int days = 1;
        long cur = 0;
        for (int x : w) {
            if (cur + x > cap) { days++; cur = 0; }
            cur += x;
        }
        return days;
    }

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        int days = nextInt();
        int[] w = new int[n];
        long sum = 0;
        int max = 0;
        for (int i = 0; i < n; i++) {
            w[i] = nextInt();
            sum += w[i];
            max = Math.max(max, w[i]);
        }
        long lo = max, hi = sum;
        while (lo < hi) {
            long mid = (lo + hi) >>> 1;
            if (daysNeeded(w, mid) <= days) hi = mid; else lo = mid + 1;
        }
        System.out.println(lo);
    }

    // ===================== быстрый ввод =====================
    private static final InputStream IN = System.in;
    private static final byte[] BUF = new byte[1 << 16];
    private static int bufLen = 0, bufPtr = 0;
    private static byte[] tok = new byte[64];   // последний прочитанный токен
    private static int tokLen = 0;

    private static int readByte() throws IOException {
        if (bufPtr == bufLen) {
            bufLen = IN.read(BUF, 0, BUF.length);
            bufPtr = 0;
            if (bufLen <= 0) { bufLen = 0; return -1; }
        }
        return BUF[bufPtr++] & 0xFF;
    }

    private static long nextLong() throws IOException {
        int c = readByte();
        while (c != '-' && (c < '0' || c > '9')) {
            if (c == -1) throw new EOFException("неожиданный конец ввода");
            c = readByte();
        }
        boolean neg = false;
        if (c == '-') { neg = true; c = readByte(); }
        long r = 0;
        while (c >= '0' && c <= '9') { r = r * 10 + (c - '0'); c = readByte(); }
        return neg ? -r : r;
    }

    private static int nextInt() throws IOException { return (int) nextLong(); }

    /** Читает токен (последовательность непробельных символов) в tok[0..tokLen). false — конец ввода. */
    private static boolean readToken() throws IOException {
        int c = readByte();
        while (c != -1 && c <= ' ') c = readByte();
        if (c == -1) return false;
        tokLen = 0;
        while (c > ' ') {
            if (tokLen == tok.length) tok = Arrays.copyOf(tok, tokLen * 2);
            tok[tokLen++] = (byte) c;
            c = readByte();
        }
        return true;
    }

    private static String next() throws IOException {
        return readToken() ? new String(tok, 0, tokLen, java.nio.charset.StandardCharsets.ISO_8859_1) : null;
    }
}
