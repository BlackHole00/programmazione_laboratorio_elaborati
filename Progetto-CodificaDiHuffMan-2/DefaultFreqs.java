// Progetto: Huffman - parte 2

// Francesco Viciguerra - 29/05/2024
// 		(viciguerrafrancesco@gmail.com)
// 		(166896@spes.uniud.it)
// In riferimento a: https://users.dimi.uniud.it/~claudio.mirolo/teaching/programmazione/laboratory/assignments_23_24/29-05-24.pdf

// La classe DefaultFreqs fornisce una stima statistica (calcolata una singola
// volta, in quanto l'interita'della classe e'static e final) della frequenza
// dei caratteri nella lingua inglese, per come e'stato spiegato nella consegna
//
// Si nota che per motivi di leggibilita'e'stato utilizzato un blocco statico,
// sebbene quest'ultimo non e'mai stato spiegato a lezione.
//
// La variabile statica DEFAULT_ENGLISH_FREQS viene infatti utilizzata per 
// creare l'albero di Huffman e contiene le frequenze di default di ogni 
// carattere su un testo statistico di 100000 caratteri

public class DefaultFreqs {
    public static final int[] DEFAULT_ENGLISH_FREQS = new int[128];

    private static final int SIMULATED_TEXT_LENGTH = 100000;
    private static final float MEDIUM_WORDS_IN_PHRASE = 24.5f;
    private static final float MEDIUM_WORD_LENGTH = 5.1f; // in letters
    private static final float MEDIUM_PHRASE_LENGTH =
        MEDIUM_WORDS_IN_PHRASE * (MEDIUM_WORD_LENGTH);
    private static final float MEDIUM_POINTS_PER_TEXT =
        SIMULATED_TEXT_LENGTH / (MEDIUM_PHRASE_LENGTH + 1);
    private static final float MEDIUM_COMMAS_PER_TEXT =
        MEDIUM_POINTS_PER_TEXT / 100.0f * 45.0f;
    private static final float MEDIUM_APOSTROPHES_PER_TEXT =
        MEDIUM_POINTS_PER_TEXT / 100.0f * 40.0f;
    private static final float MEDIUM_COLONS_PER_TEXT =
        MEDIUM_POINTS_PER_TEXT / 100.0f * 5.0f;
    private static final float MEDIUM_SEMICOLONS_PER_TEXT =
        MEDIUM_POINTS_PER_TEXT / 100.0f * 5.0f;
    private static final float MEDIUM_APICES_PER_TEXT =
        MEDIUM_POINTS_PER_TEXT / 100.0f * 5.0f;
    private static final float MEDIUM_SPACES_PER_TEXT =
        SIMULATED_TEXT_LENGTH / (MEDIUM_WORD_LENGTH + 1) + 
        MEDIUM_SEMICOLONS_PER_TEXT + 
        MEDIUM_COLONS_PER_TEXT + 
        MEDIUM_COMMAS_PER_TEXT;
    private static final float MEDIUM_LETTERS_PER_TEXT =
        SIMULATED_TEXT_LENGTH - MEDIUM_SPACES_PER_TEXT - MEDIUM_APICES_PER_TEXT -
        MEDIUM_SEMICOLONS_PER_TEXT - MEDIUM_COLONS_PER_TEXT - 
        MEDIUM_APOSTROPHES_PER_TEXT - MEDIUM_COMMAS_PER_TEXT - 
        MEDIUM_POINTS_PER_TEXT;
    private static final float MEDIUM_LOWER_LETTERS_PER_TEXT =
        (MEDIUM_LETTERS_PER_TEXT / MEDIUM_PHRASE_LENGTH) * (MEDIUM_PHRASE_LENGTH - 1.0f);
    private static final float MEDIUM_UPPER_LETTERS_PER_TEXT =
        (MEDIUM_LETTERS_PER_TEXT / MEDIUM_PHRASE_LENGTH) * (1.0f);
    private static final int UNKNOWN_CHAR_FREQUENCY = 1; 
 
    static {
        DEFAULT_ENGLISH_FREQS['a'] = (int)(0.08167f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['b'] = (int)(0.01492f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['c'] = (int)(0.02782f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['d'] = (int)(0.04253f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['e'] = (int)(0.12702f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['f'] = (int)(0.02228f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['g'] = (int)(0.02015f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['h'] = (int)(0.06094f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['i'] = (int)(0.06966f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['j'] = (int)(0.00153f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['k'] = (int)(0.00772f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['l'] = (int)(0.04025f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['m'] = (int)(0.02406f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['n'] = (int)(0.06749f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['o'] = (int)(0.07507f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['p'] = (int)(0.01929f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['q'] = (int)(0.00095f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['r'] = (int)(0.05987f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['s'] = (int)(0.06327f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['t'] = (int)(0.09056f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['u'] = (int)(0.02758f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['v'] = (int)(0.00978f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['w'] = (int)(0.02361f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['x'] = (int)(0.00150f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['y'] = (int)(0.01974f * MEDIUM_LOWER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['z'] = (int)(0.00074f * MEDIUM_LOWER_LETTERS_PER_TEXT);

        DEFAULT_ENGLISH_FREQS['A'] = (int)(0.08167f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['B'] = (int)(0.01492f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['C'] = (int)(0.02782f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['D'] = (int)(0.04253f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['E'] = (int)(0.12702f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['F'] = (int)(0.02228f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['G'] = (int)(0.02015f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['H'] = (int)(0.06094f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['I'] = (int)(0.06966f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['J'] = (int)(0.00153f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['K'] = (int)(0.00772f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['L'] = (int)(0.04025f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['M'] = (int)(0.02406f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['N'] = (int)(0.06749f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['O'] = (int)(0.07507f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['P'] = (int)(0.01929f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['Q'] = (int)(0.00095f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['R'] = (int)(0.05987f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['S'] = (int)(0.06327f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['T'] = (int)(0.09056f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['U'] = (int)(0.02758f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['V'] = (int)(0.00978f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['W'] = (int)(0.02361f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['X'] = (int)(0.00150f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['Y'] = (int)(0.01974f * MEDIUM_UPPER_LETTERS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['Z'] = (int)(0.00074f * MEDIUM_UPPER_LETTERS_PER_TEXT);

        DEFAULT_ENGLISH_FREQS[' '] = (int)(MEDIUM_SPACES_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['.'] = (int)(MEDIUM_POINTS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS[','] = (int)(MEDIUM_COMMAS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['\''] = (int)(MEDIUM_APOSTROPHES_PER_TEXT);
        DEFAULT_ENGLISH_FREQS[':'] = (int)(MEDIUM_COLONS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS[';'] = (int)(MEDIUM_SEMICOLONS_PER_TEXT);
        DEFAULT_ENGLISH_FREQS['"'] = (int)(MEDIUM_APICES_PER_TEXT);

        for (int i = 0; i < DEFAULT_ENGLISH_FREQS.length; i++) {
            if (DEFAULT_ENGLISH_FREQS[i] == 0) {
                DEFAULT_ENGLISH_FREQS[i] = UNKNOWN_CHAR_FREQUENCY;
            }
        }
    }
}
