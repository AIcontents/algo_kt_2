import java.io.*;
import java.util.*;

/**
 * КТ_2, задача 3. Объединение интервалов.
 * Идея: сортируем интервалы по началу и идём слева направо, поддерживая текущий объединённый интервал.
 * Если начало следующего <= конца текущего (пересечение или общая граница) — расширяем конец, иначе выводим текущий.
 * Время O(n log n).
 * Формат вывода в условии не указан: каждый итоговый интервал печатается строкой «start end».
 */
public class Main {
    public static void main(String[] args) throws IOException {
        final long OFF = 1_000_000_000L;
        int n = nextInt();
        long[] key = new long[n];                    // пара (start, end) упакована в одно long: сортировка по start, затем по end
        for (int i = 0; i < n; i++) {
            long s = nextLong(), e = nextLong();
            key[i] = ((s + OFF) << 32) | (e + OFF);
        }
        Arrays.sort(key);

        StringBuilder sb = new StringBuilder();
        boolean has = false;
        long curS = 0, curE = 0;
        for (long k : key) {
            long s = (k >>> 32) - OFF, e = (k & 0xFFFFFFFFL) - OFF;
            if (!has) {
                curS = s; curE = e; has = true;
            } else if (s <= curE) {
                if (e > curE) curE = e;
            } else {
                sb.append(curS).append(' ').append(curE).append('\n');
                curS = s; curE = e;
            }
        }
        if (has) sb.append(curS).append(' ').append(curE).append('\n');
        System.out.print(sb);
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
