import java.io.*;
import java.util.*;

/**
 * КТ_2, задача 6. Минимальное количество переговорных комнат (встречи [start, end)).
 * Идея: отдельно сортируем начала и концы. Идём по началам: если самая ранняя из занятых комнат
 * освободилась (end <= start), переиспользуем её, иначе нужна новая. Ответ — максимум одновременно занятых комнат.
 * Время O(n log n).
 */
public class Main {
    public static void main(String[] args) throws IOException {
        int n = nextInt();
        int[] st = new int[n], en = new int[n];
        for (int i = 0; i < n; i++) {
            st[i] = nextInt();
            en[i] = nextInt();
        }
        Arrays.sort(st);
        Arrays.sort(en);
        int busy = 0, best = 0, j = 0;
        for (int i = 0; i < n; i++) {
            while (j < n && en[j] <= st[i]) { j++; busy--; }   // момент end уже свободен
            busy++;
            best = Math.max(best, busy);
        }
        System.out.println(best);
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
